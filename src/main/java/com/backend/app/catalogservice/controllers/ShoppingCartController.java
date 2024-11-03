package com.backend.app.catalogservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.app.catalogservice.models.ShoppingCartModel.ShoppingCartResponse;
import com.backend.app.catalogservice.models.ShoppingCartModel.AddItemRequest;
import com.backend.app.catalogservice.services.ShoppingCartService;
import com.backend.app.shared.libraries.http.BaseResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

interface IShoppingCartController {
    public ResponseEntity<BaseResponse<ShoppingCartResponse>> getCartItemsByUserId(String userId);
    public ResponseEntity<BaseResponse<?>> addItemToCart( AddItemRequest request);
    public ResponseEntity<BaseResponse<?>> deleteCartItem(String customerId, String bookId);
}

@RestController
@RequestMapping("/api/v1/cart")
public class ShoppingCartController implements IShoppingCartController {

    private ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping
    @Tag(name = "Cart item", description = "Methods of cart item APIs")
    @Operation(summary = "Get cart items by user id", description = "Get cart items by user id")
    public ResponseEntity<BaseResponse<ShoppingCartResponse>> getCartItemsByUserId(
            @Parameter(description = "ID of user", required = true)
            @RequestParam("userId") String userId) {
        try {
            if (userId == null || userId.isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
                        .body(new BaseResponse<>(4000, "User id is required", null));
            }

            BaseResponse<ShoppingCartResponse> response = shoppingCartService.getCartItemsByUserId(userId);
            if (response.getCode() != 2000) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(response);
            }
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(response);

        } catch (Exception e) {
            e.printStackTrace();
            String error = String.format("Internal server error: %s", e.getMessage());
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse<>(5000, error, null));
        }
    }

    @PostMapping("/add-item")
    @Tag(name = "Cart item", description = "Methods of cart item APIs")
    @Operation(summary = "Add item to cart", description = "Add item to cart by book id and customer id")
    public ResponseEntity<BaseResponse<?>> addItemToCart(@RequestBody AddItemRequest request) {
        try {
            if (request.getBookId() == null || request.getBookId().isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
                        .body(new BaseResponse<>(4000, "Book id is required", null));
            }

            if (request.getCustomerId() == null || request.getCustomerId().isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
                        .body(new BaseResponse<>(4000, "User id is required", null));
            }

            if (request.getQuantity() == null || request.getQuantity() <= 0) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
                        .body(new BaseResponse<>(4000, "Quantity is required", null));
            }

            BaseResponse<?> response = shoppingCartService.addItemToCart(request);
            if (response.getCode() != 2000) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(response);
            }
            return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(response);

        } catch (Exception e) {
            e.printStackTrace();
            String error = String.format("Internal server error: %s", e.getMessage());
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse<>(5000, error, null));

        }
    }

    @DeleteMapping("/delete-item")
    @Tag(name = "Cart item", description = "Methods of cart item APIs")
    @Operation(summary = "Delete item from cart", description = "Delete item from cart by book id and customer id")
    public ResponseEntity<BaseResponse<?>> deleteCartItem(
            @Parameter(description = "ID of customer", required = true) @RequestParam("customerId") String customerId,

            @Parameter(description = "ID of book to be deleted", required = true) @RequestParam("bookId") String bookId) {
        try {
            if (customerId == null || customerId.isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
                        .body(new BaseResponse<>(4000, "Customer id is required", null));
            }
            if (bookId == null || bookId.isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
                        .body(new BaseResponse<>(4000, "Book id is required", null));
            }
            BaseResponse<?> response = shoppingCartService.deleteCartItem(customerId, bookId);
            if (response.getCode() != 2000) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(response);
            }
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            String error = String.format("Internal server error: %s", e.getMessage());
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse<>(5000, error, null));
        }
    }
}
