package com.backend.app.catalogservice.book.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.backend.app.catalogservice.book.models.ListBook;
import com.backend.app.shared.models.pagination.DataPagination;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class GetBookRepository {

  @PersistenceContext
  private EntityManager entityManager;

  public List<ListBook> getBooksPaged(DataPagination dataPagination, Integer isActive) {
    try {
      String queryString = """
              SELECT
                b.id,
                b.isbn,
                b.title,
                b.genre,
                b.publication_year,
                b.copies_available,
                b.price,
                p.name AS publisher
              FROM books b
              LEFT JOIN publishers p ON b.publisher_id = p.id
          """;

      if (!dataPagination.getSearch().isEmpty()) {
        queryString += String.format(
            """
            WHERE (
              b.title LIKE '%%%s%%'
                OR b.isbn LIKE '%%%s%%'
                OR b.genre LIKE '%%%s%%'
                OR p.name LIKE '%%%s%%'
            )
            """, 
            dataPagination.getSearch(), 
            dataPagination.getSearch(), 
            dataPagination.getSearch(),
            dataPagination.getSearch()
        );
      }

      if (isActive != null) {
        queryString += String.format(" AND b.is_active = %d", isActive);
      }

      // ORDER BY
      queryString += String.format(
          " ORDER BY b.created_date %s LIMIT %d OFFSET %d",
          dataPagination.getOrderName(),
          dataPagination.getLimit(),
          dataPagination.getOffset());

      Query selectQuery = entityManager.createNativeQuery(queryString);
      List<Object[]> resultList = selectQuery.getResultList();

      // Map the results manually
      List<ListBook> books = resultList.stream().map(row -> new ListBook(
          (String) row[0], // id
          (String) row[1], // isbn
          (String) row[2], // title
          (String) row[3], // genre
          (Date) row[4], // publicationYear
          (Integer) row[5], // copiesAvailable
          (Double) row[6], // price
          (String) row[7] // publisher
      )).toList();

      if (books.isEmpty()) {
        return List.of();
      }
      return books;
    } catch (Exception exception) {
      throw exception;
    }
  }
}
