package com.backend.app.catalogservice.book.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.backend.app.shared.models.entities.Book;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

interface IBookRepository {
  Boolean createBooks(List<Book> books);
  
}

@Repository
public class BookRepository implements IBookRepository{
  
  @PersistenceContext
  private EntityManager entityManager;

  @Transactional
  public Boolean createBooks(List<Book> books) {
    try {
      books.forEach(book -> entityManager.persist(book));
      return true;
    } catch (Exception exception) {
      throw exception;
    }
  }
}
