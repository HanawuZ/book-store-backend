package com.backend.app.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.backend.app.models.entities.Author;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Repository
public class AuthorRepository {
    
    private EntityManager entityManager;

    @Autowired
    public AuthorRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public List<Author> getAuthors(Set<String> authorIds) {
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM authors WHERE id IN (:authorIds)", Author.class)
                    .setParameter("authorIds", authorIds);
            List<Author> result =  query.getResultList();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
