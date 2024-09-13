package com.backend.app.orderservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.app.orderservice.models.CreateOrderRequest;
import com.backend.app.orderservice.services.OrderService;
import com.backend.app.shared.libraries.http.BaseResponse;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/orders")
public class OrderController {
    
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<String>> createOrder(@RequestBody CreateOrderRequest request) {
        try {
            if (request.getCustomerId() == null || request.getCustomerId().isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new BaseResponse<>(4000, "Customer id is required", null));
            }

            if (request.getCustomerAddresssId() == null || request.getCustomerAddresssId().isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new BaseResponse<>(4000, "Customer address id is required", null));
            }

            BaseResponse<String> response = orderService.createOrder(request);
            if (response.getCode() != 2001) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(response);
            }

            return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(response);
        } catch (Exception exception) {
            exception.printStackTrace();
            String error = String.format("Internal server error: %s", exception.getMessage());
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(new BaseResponse<>(5000, error, null));
        }
    }
}
