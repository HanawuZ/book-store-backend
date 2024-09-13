package com.backend.app.orderservice.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.backend.app.shared.models.entities.CustomerAddress;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class CustomerAddressRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<CustomerAddress> getCustomerAddressById(String id) {
        try {

            Object result = entityManager
                .createNativeQuery("SELECT * FROM customer_addresses WHERE id = :id", CustomerAddress.class)
                .setParameter("id", id)
                .getSingleResult();

            if (result == null) {
                return Optional.empty();
            }

            return Optional.of((CustomerAddress) result);    

        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }
}
