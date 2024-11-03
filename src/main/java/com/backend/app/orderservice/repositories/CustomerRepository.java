package com.backend.app.orderservice.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.backend.app.shared.models.entities.Customer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

interface ICustomerRepository {
    Optional<Customer> getCustomerById(String id);
}

@Repository
public class CustomerRepository implements ICustomerRepository {
    
    @PersistenceContext
    private EntityManager entityManager;

    public Optional<Customer> getCustomerById(String id) {
        try {

            Object result = entityManager.createNativeQuery("SELECT * FROM customers WHERE id = :id", Customer.class)
                .setParameter("id", id)
                .getSingleResult();

            if (result == null) {
                return Optional.empty();
            }

            return Optional.of((Customer) result);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }
}
