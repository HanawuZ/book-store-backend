package com.backend.app.catalogservice.cart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.app.shared.error.ErrorMessage;
import com.backend.app.shared.libraries.http.BaseResponse;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/v1/carts")
@CrossOrigin
public class CreateCartController {
  
  @PostMapping
  public ResponseEntity<BaseResponse<String>> addItemsIntoCart() {
    try {
      return null;
    } catch (Exception exception) {
      exception.printStackTrace();
      String error = ErrorMessage.getErrorMessage(exception);
      BaseResponse<String> errorResponse = new BaseResponse<String>(5000, error, null);
      return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(errorResponse);
    }
  }
}
