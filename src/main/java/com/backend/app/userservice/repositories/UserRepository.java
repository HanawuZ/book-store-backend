package com.backend.app.userservice.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.backend.app.shared.models.entities.Customer;
import com.backend.app.shared.models.entities.User;
import com.backend.app.shared.models.entities.UserMapping;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class UserRepository {
    
    @PersistenceContext
    private EntityManager entityManager;

    public Optional<User> findByUsernameOrEmail(String username, String email) {
        try {
            Object user = entityManager.createNativeQuery("SELECT * FROM users WHERE username = :username OR email = :email", User.class)
                .setParameter("username", username)
                .setParameter("email", email)
                .getSingleResult();
            return Optional.of((User) user);
        } catch (NoResultException exception) {
            return Optional.empty();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }
    public Optional<User> findById(String id) {
        try {
            Object user = entityManager.createNativeQuery("SELECT * FROM users WHERE id = :id", User.class)
                .setParameter("id", id)
                .getSingleResult();
            if (user == null) {
                return Optional.empty();
            }
            return Optional.of((User) user);

        } catch (Exception exception) {
            exception.printStackTrace();
            return Optional.empty();
        }
    }

    @Transactional
    public Boolean createCustomer(User user, Customer customer, UserMapping userMapping) {
        try {

            entityManager.persist(user);
            entityManager.persist(customer);
            entityManager.persist(userMapping);

            entityManager.flush();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    @Transactional
    public Boolean updateUser(User user) {
        try {
            User updatedUser = entityManager.merge(user);
            entityManager.flush();

            if (updatedUser == null) {
                return false;
            }
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

} 
