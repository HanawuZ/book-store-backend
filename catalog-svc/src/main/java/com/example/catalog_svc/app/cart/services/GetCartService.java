package com.example.catalog_svc.app.cart.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.catalog_svc.app.cart.models.query.CartItem;
import com.example.catalog_svc.app.cart.repositories.GetCartRepository;
import com.example.catalog_svc.models.response.BaseResponse;

@Service
public class GetCartService {
  private GetCartRepository getCartRepository;

  public GetCartService(GetCartRepository getCartRepository) {
    this.getCartRepository = getCartRepository;
  }

  public BaseResponse<List<CartItem>> getCartItemByCustomer(String customerId) {
    try {

      List<CartItem> cartItems = getCartRepository.getCartItemsByCustomerId(customerId);
      if (cartItems.isEmpty()) {
        return new BaseResponse<>(4000, "Cart is empty", null);
      }

      System.out.println("Get cart items");

      return new BaseResponse<>(2000, "Success", cartItems);

    } catch (Exception exception) {
      throw exception;
    }
  }
}
