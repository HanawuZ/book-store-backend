package com.backend.app.catalogservice.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.app.catalogservice.repositories.CartRepository;
import com.backend.app.catalogservice.models.ShoppingCartModel.CartQueryResult;
import com.backend.app.catalogservice.models.ShoppingCartModel.ShoppingCartResponse;
import com.backend.app.catalogservice.models.ShoppingCartModel.CartItem;
import com.backend.app.shared.libraries.http.BaseResponse;
@Service
public class ShoppingCartService {
    
    private CartRepository cartRepository;

    public ShoppingCartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public BaseResponse<ShoppingCartResponse> getCartItemsByUserId(String userId){
        try {
            List<CartQueryResult> results = cartRepository.getCartItemsByUserId(userId);
            if (results.isEmpty()) {
                return new BaseResponse<>(4000, "Cart is empty", null);
            }

            ShoppingCartResponse shoppingCart = new ShoppingCartResponse();
            
            List<CartItem> cartItems =  new ArrayList<>();
            Double totalPrice = 0.0;

            for (CartQueryResult result : results) {
                totalPrice += result.getPrice() * result.getQuantity();
                CartItem item = new CartItem();
                item.setBookId(result.getBookId());
                item.setTitle(result.getTitle());
                item.setGenre(result.getGenre());
                item.setPrice(result.getPrice());
                item.setQuantity(result.getQuantity());

                cartItems.add(item);
            }

            shoppingCart.setTotalPrice(totalPrice);
            shoppingCart.setItems(cartItems);

            return new BaseResponse<>(2000, "Cart items retrieved successfully", shoppingCart);
        } catch (Exception e) {
            e.printStackTrace();
            String error = String.format("Internal server error: %s", e.getMessage());
            return new BaseResponse<>(5000, error, null);
        }
    }
}
