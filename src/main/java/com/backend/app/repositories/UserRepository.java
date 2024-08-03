package com.backend.app.repositories;

import org.springframework.stereotype.Repository;

import com.backend.app.models.entities.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class UserRepository {

    private EntityManager entityManager;

    @Autowired
    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<User> getUserByUsername(String username) {
        try {
            Query query = entityManager.createNativeQuery(
                    "SELECT * FROM users u WHERE u.username = :username", User.class)
                    .setParameter("username", username);
    
            Object result = query.getSingleResult();
            if (result == null) {
                return Optional.empty();
            }

            return Optional.of((User) result);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<User> getUserByProviderId (String providerId) {
        try {
            Query query = entityManager.createNativeQuery(
                    "SELECT * FROM users u WHERE u.provider_id = :providerId", User.class)
                    .setParameter("providerId", providerId);

            Object result = query.getSingleResult();
            if (result == null) {
                return Optional.empty();
            }
            return Optional.of((User) result);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Transactional
    public Boolean createUser(User user) {
        try {
            entityManager.persist(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public Boolean updateUser(User user) {
        try {
            entityManager.merge(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
