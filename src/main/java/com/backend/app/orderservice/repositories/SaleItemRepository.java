package com.backend.app.orderservice.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.backend.app.orderservice.models.query.CartItemQueryResult;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import com.backend.app.shared.models.entities.SaleItem;
import com.backend.app.shared.models.entities.SaleOrder;

@Repository
public class SaleItemRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<CartItemQueryResult> getCartItemByCustomerId(String customerId) {
        try {

            List<CartItemQueryResult> results = entityManager.createNativeQuery(
                    "SELECT b.id as product_id, b.genre, b.isbn, b.title, c.quantity, b.price, b.publication_year, " +
                    "cus.firstname as customer_firstname, cus.lastname as customer_lastname, " +
                    "cus.phone_one as customer_phone_one, cus.phone_two as customer_phone_two, " +
                    "p.name as publisher_name " +
                    "FROM carts c " +
                    "LEFT JOIN books b ON c.book_id = b.id " +
                    "LEFT JOIN customers cus ON c.customer_id = cus.id " +
                    "LEFT JOIN publishers p ON b.publisher_id = p.id " +
                    "WHERE c.customer_id = :customerId", CartItemQueryResult.class)
                .setParameter("customerId", customerId)
                .getResultList();

            if (results.isEmpty()) {
                return List.of();
            } 
            return results;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    @Transactional
    public Boolean createSaleOrder(SaleOrder saleOrder, List<SaleItem> saleItems, String customerId) {
        try {

            // Update copiesAvailable in table `books`
            String updateQuery = "UPDATE books SET copies_available = copies_available - :quantity WHERE id = :bookId";
            for (SaleItem saleItem : saleItems) {
                entityManager.createNativeQuery(updateQuery)
                    .setParameter("quantity", saleItem.getQuantity())
                    .setParameter("bookId", saleItem.getProductId())
                    .executeUpdate();
            }
            

            entityManager.persist(saleOrder);
            saleItems.forEach(entityManager::persist);

            Integer affectedRows = entityManager.createNativeQuery("DELETE FROM carts WHERE customer_id = :customerId")
                .setParameter("customerId", customerId)
                .executeUpdate();
            
            entityManager.flush();
            return affectedRows > 0;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }


}
