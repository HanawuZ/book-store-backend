package com.backend.app.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

import com.backend.app.models.entities.Author;
import com.backend.app.models.entities.Book;
@Repository
public class BookRepository {

    private EntityManager entityManager;

    @Autowired
    public BookRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Book> getAllBooks() {
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM books", Book.class);
            List<Book> result = query.getResultList();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Optional<Book> getBookById(String id) {
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM books WHERE id = :id", Book.class)
                    .setParameter("id", id);
            List<Book> result = query.getResultList();
            if (result.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(result.get(0));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<Book> createBook(Book book) {
        try {
            entityManager.persist(book);
            return Optional.of(book);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


}
