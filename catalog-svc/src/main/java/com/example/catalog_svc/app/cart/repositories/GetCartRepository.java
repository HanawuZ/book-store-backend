package com.example.catalog_svc.app.cart.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.catalog_svc.models.entities.Cart;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class GetCartRepository {

  @PersistenceContext
  private EntityManager entityManager;

  public List<Cart> getCartByCustomerId(String customerId) {
    try {
      List<Cart> results = entityManager
          .createNativeQuery("SELECT * FROM carts WHERE customer_id = :customerId", Cart.class)
          .setParameter("customerId", customerId)
          .getResultList();

      return results;
    } catch (Exception exception) {
      throw exception;
    }
  }

}
