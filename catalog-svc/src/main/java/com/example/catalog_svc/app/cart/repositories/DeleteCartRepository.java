package com.example.catalog_svc.app.cart.repositories;

import org.springframework.stereotype.Repository;

import com.example.catalog_svc.models.entities.Cart;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class DeleteCartRepository {
  
  @PersistenceContext
  private EntityManager entityManager;

  @Transactional
  public Boolean deleteCartItem(String customerId) {
    try {
      Integer affectedRows = entityManager
          .createNativeQuery("DELETE FROM carts WHERE customer_id = :customerId", Cart.class)
          .setParameter("customerId", customerId)
          .executeUpdate();

      return affectedRows > 0;
    } catch (Exception exception) {
      throw exception;
    }
  }
}
