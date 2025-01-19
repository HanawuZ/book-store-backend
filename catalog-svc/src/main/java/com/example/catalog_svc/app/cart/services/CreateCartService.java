package com.example.catalog_svc.app.cart.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.catalog_svc.app.cart.models.request.AddItemToCart;
import com.example.catalog_svc.app.cart.repositories.CreateCartRepository;
import com.example.catalog_svc.app.cart.repositories.GetCartRepository;
import com.example.catalog_svc.models.entities.Cart;
import com.example.catalog_svc.models.response.BaseResponse;

@Service
public class CreateCartService {

  private CreateCartRepository createCartRepository;
  private GetCartRepository getCartRepository;

  public CreateCartService(
      CreateCartRepository createCartRepository,
      GetCartRepository getCartRepository) {
    this.createCartRepository = createCartRepository;
    this.getCartRepository = getCartRepository;
  }

  public BaseResponse<String> addItemToCart(AddItemToCart request) {
    try {
      if (request.getQuantity() <= 0) {
        return new BaseResponse<>(4000, "Quantity must be greater than 0", null);
      }

      if (request.getBookId() == null || request.getBookId().isEmpty()) {
        return new BaseResponse<>(4000, "Book id is required", null);
      }

      if (request.getCustomerId() == null || request.getCustomerId().isEmpty()) {
        return new BaseResponse<>(4000, "Customer id is required", null);
      }

      List<Cart> carts = getCartRepository.getCartByCustomerId(request.getCustomerId());
      
      Cart newItem = new Cart();
      
      newItem.setId(UUID.randomUUID().toString());
      newItem.setBookId(request.getBookId());
      newItem.setCustomerId(request.getCustomerId());
      newItem.setQuantity(request.getQuantity());

      Timestamp createdDate = new Timestamp(System.currentTimeMillis());
      newItem.setCreatedDate(createdDate);

      Timestamp updatedDate = new Timestamp(System.currentTimeMillis());
      newItem.setUpdatedDate(updatedDate);

      for (Integer i = 0; i < carts.size(); i++) {
        
        Cart item = carts.get(i);
        String bookId = item.getBookId();
        Integer quantity = item.getQuantity();
        
        System.out.printf("id: %s, bookId: %s, customerId: %s\n", item.getId(), bookId, request.getCustomerId());
        System.out.println(request.getBookId());
        System.out.println(request.getBookId().equals(bookId));
        if (request.getBookId().equals(bookId)) {
          Integer newQty = quantity + request.getQuantity();
          System.out.println("Matched book");
          newItem.setId(item.getId());
          newItem.setQuantity(newQty);
          newItem.setCreatedDate(createdDate);
          
          break;
        }
      }

      if (createCartRepository.upsertCartItem(newItem)) {
        return new BaseResponse<>(2001, "Success", null);
      }

      return new BaseResponse<>(4000, "Failed to add item", null);
    } catch (Exception exception) {
      throw exception;
    }
  }

}
