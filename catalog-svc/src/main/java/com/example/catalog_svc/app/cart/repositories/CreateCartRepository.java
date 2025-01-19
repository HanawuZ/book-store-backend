package com.example.catalog_svc.app.cart.repositories;

import org.springframework.stereotype.Repository;

import com.example.catalog_svc.models.entities.Cart;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class CreateCartRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Transactional
  public Boolean upsertCartItem(Cart cart) {
    try {
      Integer affectedRows = entityManager
          .createNativeQuery(
              "INSERT INTO carts (id, customer_id, book_id, quantity, created_date, updated_date)\n" + //
              "VALUES (:id, :customerId, :bookId, :quantity, :createdDate, :updatedDate)\n" + //
              "ON CONFLICT (id)\n" + //
              "DO UPDATE SET quantity = :quantity, updated_date = :updatedDate",
              Cart.class)
          .setParameter("id", cart.getId())
          .setParameter("customerId", cart.getCustomerId())
          .setParameter("bookId", cart.getBookId())
          .setParameter("quantity", cart.getQuantity())
          .setParameter("createdDate", cart.getCreatedDate())
          .setParameter("updatedDate", cart.getUpdatedDate())
          .executeUpdate();

      return affectedRows > 0;

    } catch (Exception exception) {
      throw exception;
    }
  }

}
