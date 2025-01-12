package com.example.catalog_svc.app.book.repositories;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.catalog_svc.app.book.models.ListBook;
import com.example.catalog_svc.models.pagination.DataPagination;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class GetBookRepository {

  @PersistenceContext
  private EntityManager entityManager;

  public List<ListBook> getBooksPaged(
      DataPagination dataPagination,
      Integer isActive,
      Integer minPrice, Integer maxPrice,
      List<String> genres) {
    try {
      Boolean hasFilters = false;
      List<String> filters = new ArrayList<String>();

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

      if (genres != null && !genres.isEmpty()) {
        hasFilters = true;

        String genreInClause = String.format("b.genre IN ('%s')", String.join("','", genres));
        filters.add(genreInClause);

      }

      if (!dataPagination.getSearch().isEmpty()) {
        hasFilters = true;
        filters.add(String.format(
            "(b.title LIKE '%%%s%%' OR b.isbn LIKE '%%%s%%' OR b.genre LIKE '%%%s%%' OR p.name LIKE '%%%s%%')",
            dataPagination.getSearch(),
            dataPagination.getSearch(),
            dataPagination.getSearch(),
            dataPagination.getSearch()));
      }

      if (isActive != null) {
        hasFilters = true;
        filters.add(String.format("b.is_active = %d", isActive));
      }

      if (minPrice != null) {
        hasFilters = true;
        filters.add(String.format("b.price >= %d", minPrice));
      }

      if (maxPrice != null) {
        hasFilters = true;
        filters.add(String.format("b.price <= %d", maxPrice));
      }

      if (hasFilters) {
        queryString += " WHERE " + String.join(" AND ", filters);
      }

      // ORDER BY
      queryString += String.format(
          " ORDER BY b.created_date %s LIMIT %d OFFSET %d",
          dataPagination.getOrderName(),
          dataPagination.getLimit(),
          dataPagination.getOffset());

      System.out.println(queryString);

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