package com.backend.app.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

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
}
