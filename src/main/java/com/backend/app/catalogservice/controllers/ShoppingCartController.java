package com.backend.app.catalogservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.app.catalogservice.models.ShoppingCartModel.ShoppingCartResponse;
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
}
