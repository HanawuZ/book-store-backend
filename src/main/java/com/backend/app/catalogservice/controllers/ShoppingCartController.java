package com.backend.app.catalogservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.app.catalogservice.models.ShoppingCartModel.ShoppingCartResponse;
import com.backend.app.catalogservice.models.ShoppingCartModel.AddItemRequest;
import com.backend.app.catalogservice.services.ShoppingCartService;
import com.backend.app.shared.libraries.http.BaseResponse;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/cart")
public class ShoppingCartController {
    
    private ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    public ResponseEntity<BaseResponse<ShoppingCartResponse>> getCartItemsByUserId(@RequestParam("userId") String userId) {
        try {
            if (userId == null || userId.isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new BaseResponse<>(4000, "User id is required", null));
            }

            BaseResponse<ShoppingCartResponse> response = shoppingCartService.getCartItemsByUserId(userId);
            if (response.getCode() != 2000) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(response);
            }
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(response);

        } catch (Exception e) {
            e.printStackTrace();
            String error = String.format("Internal server error: %s", e.getMessage());
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(new BaseResponse<>(5000, error, null));
        }
    }

    @PostMapping("/add-item")
    public ResponseEntity<BaseResponse<?>> addItemToCart(@RequestBody AddItemRequest request) {        
        try {
            if (request.getBookId() == null || request.getBookId().isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new BaseResponse<>(4000, "Book id is required", null));
            }

            if (request.getUserId() == null || request.getUserId().isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new BaseResponse<>(4000, "User id is required", null));
            }

            if (request.getQuantity() == null || request.getQuantity() <= 0) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new BaseResponse<>(4000, "Quantity is required", null));
            }

            BaseResponse<?> response = shoppingCartService.addItemToCart(request);
            if (response.getCode() != 2000) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(response);
            }
            return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(response);
            
        } catch (Exception e) {
            e.printStackTrace();
            String error = String.format("Internal server error: %s", e.getMessage());
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(new BaseResponse<>(5000, error, null));

        }
    }

    @PatchMapping("/update-item")
    public ResponseEntity<BaseResponse<?>> updateCartItem(@RequestBody AddItemRequest request) {
        try {
            if (request.getBookId() == null || request.getBookId().isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new BaseResponse<>(4000, "Book id is required", null));
            }
            if (request.getUserId() == null || request.getUserId().isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new BaseResponse<>(4000, "User id is required", null));
            }

            BaseResponse<?> response = shoppingCartService.updateCartItem(request);
            if (response.getCode() != 2000) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(response);
            }
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(response);
            
        } catch (Exception e) {
            e.printStackTrace();
            String error = String.format("Internal server error: %s", e.getMessage());
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(new BaseResponse<>(5000, error, null));
        }
    }
    
    @DeleteMapping("/delete-item")
    public ResponseEntity<BaseResponse<?>> deleteCartItem(@RequestParam("userId") String userId, @RequestParam("bookId") String bookId) {
        try {
            if (userId == null || userId.isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new BaseResponse<>(4000, "User id is required", null));
            }
            if (bookId == null || bookId.isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new BaseResponse<>(4000, "Book id is required", null));
            }
            BaseResponse<?> response = shoppingCartService.deleteCartItem(userId, bookId);
            if (response.getCode() != 2000) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(response);
            }
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            String error = String.format("Internal server error: %s", e.getMessage());
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(new BaseResponse<>(5000, error, null));
        }
    }
}
