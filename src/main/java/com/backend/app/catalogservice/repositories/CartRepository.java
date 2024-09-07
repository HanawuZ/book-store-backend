package com.backend.app.catalogservice.repositories;

import org.springframework.stereotype.Repository;

import java.util.List;

import com.backend.app.catalogservice.models.ShoppingCartModel.CartQueryResult;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
@Repository
public class CartRepository {
    
    @PersistenceContext
    private EntityManager entityManager;


    public List<CartQueryResult> getCartItemsByUserId(String userId) {
        try {
            List<CartQueryResult> results = entityManager.createNativeQuery("SELECT b.id as book_id, b.title, b.genre, b.price, c.quantity FROM carts c JOIN books b ON c.book_id = b.id WHERE c.user_id = :userId", CartQueryResult.class)
                .setParameter("userId", userId)
                .getResultList();
                
            return results;
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}

