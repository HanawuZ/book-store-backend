package com.backend.app.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import com.backend.app.models.entities.Publisher;


@Repository
public class PublisherRepository {
    
    private EntityManager entityManager;

    @Autowired
    public PublisherRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Publisher> getPublisherById(String id) {
        try {
            Query query = entityManager.createNativeQuery("SELECT * FROM publishers WHERE id = :id", Publisher.class)
                    .setParameter("id", id);
            Object result = query.getSingleResult();

            if (result == null) {
                return Optional.empty();
            }

            return Optional.of((Publisher) result);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
