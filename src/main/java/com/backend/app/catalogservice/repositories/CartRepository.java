package com.backend.app.catalogservice.repositories;

import org.springframework.stereotype.Repository;

import java.util.List;

import com.backend.app.catalogservice.models.ShoppingCartModel.AddItemRequest;
import com.backend.app.catalogservice.models.ShoppingCartModel.CartQueryResult;
import com.backend.app.shared.models.entities.Cart;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;


interface ICartRepository {
    
    List<Cart> getCartByCustomerId(String customerId);
    
    Boolean upsertCartItem(AddItemRequest request);
    
    Boolean deleteItem(String customerId, String bookId);
    
}

@Repository
public class CartRepository implements ICartRepository {
    
    @PersistenceContext
    private EntityManager entityManager;

    public List<Cart> getCartByCustomerId(String customerId) {
        try {
            List<Cart> results = entityManager.createNativeQuery("SELECT * FROM carts WHERE customer_id = :customerId", Cart.class)
                .setParameter("customerId", customerId)
                .getResultList();
                
            return results;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    public List<CartQueryResult> getCartItemsByUserId(String userId) {
        try {
            List<CartQueryResult> results = entityManager.createNativeQuery("SELECT b.id as book_id, b.title, b.genre, b.price, c.quantity FROM carts c JOIN books b ON c.book_id = b.id WHERE c.user_id = :userId", CartQueryResult.class)
                .setParameter("userId", userId)
                .getResultList();
                
            return results;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    @Transactional
    public Boolean upsertCartItem(AddItemRequest request) {
        try {
            Integer affectedRows = entityManager.createNativeQuery("REPLACE INTO carts (customer_id, book_id, quantity) VALUES (:customerId, :bookId, :quantity)", Cart.class)
                .setParameter("customerId", request.getCustomerId())
                .setParameter("bookId", request.getBookId())
                .setParameter("quantity", request.getQuantity())
                .executeUpdate();
            
            return affectedRows > 0;
           
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    @Transactional
    public Boolean updateCartItemQuantity(Cart updatedItem) {
        try {
            Integer affectedRows = 0;
            if (updatedItem.getQuantity() == 0) {
                affectedRows = entityManager.createNativeQuery("DELETE FROM carts WHERE customer_id = :customerId AND book_id = :bookId", Cart.class)
                    .setParameter("userId", updatedItem.getCustomer().getId())
                    .setParameter("bookId", updatedItem.getBook().getId())
                    .executeUpdate();
            } else {
                affectedRows = entityManager.createNativeQuery("UPDATE carts SET quantity = :quantity WHERE user_id = :userId AND book_id = :bookId", Cart.class)
                    .setParameter("userId", updatedItem.getCustomer().getId())
                    .setParameter("bookId", updatedItem.getBook().getId())
                    .setParameter("quantity", updatedItem.getQuantity())
                    .executeUpdate();
            }
            return affectedRows > 0;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    @Transactional
    public Boolean deleteItem(String customerId, String bookId) {
        try {
            Integer affectedRows = entityManager.createNativeQuery("DELETE FROM carts WHERE customer_id = :customerId AND book_id = :bookId", Cart.class)
                .setParameter("customerId", customerId)
                .setParameter("bookId", bookId)
                .executeUpdate();
            return affectedRows > 0;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }
    
}

