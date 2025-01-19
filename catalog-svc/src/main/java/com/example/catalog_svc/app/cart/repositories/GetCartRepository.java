package com.example.catalog_svc.app.cart.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.catalog_svc.app.cart.models.query.CartItem;
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

  public List<CartItem> getCartItemsByCustomerId(String customerId) {
    try {

      List<CartItem> results = entityManager
          .createNativeQuery("SELECT b.id AS book_id,\n" + //
              "c.quantity,\n" + //
              "b.copies_available,\n" + //
              "b.genre,\n" + //
              "b.isbn,\n" + //
              "b.price,\n" + //
              "b.publication_year,\n" + //
              "b.title\n" + //
              "FROM carts c\n" + //
              "LEFT JOIN books b ON c.book_id = b.id\n" + //
              "WHERE c.customer_id = :customerId", CartItem.class)
          .setParameter("customerId", customerId)
          .getResultList();

      return results;
    } catch (Exception exception) {
      throw exception;
    }
  }

}
