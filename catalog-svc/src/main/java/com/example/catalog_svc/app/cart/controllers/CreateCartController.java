package com.example.catalog_svc.app.cart.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.catalog_svc.app.cart.models.request.AddItemToCart;
import com.example.catalog_svc.app.cart.services.CreateCartService;
import com.example.catalog_svc.libraries.error.ErrorMessage;
import com.example.catalog_svc.models.response.BaseResponse;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/v1/carts")
@CrossOrigin
public class CreateCartController {

  private CreateCartService createCartService;

  public CreateCartController(CreateCartService createCartService) {
    this.createCartService = createCartService;
  }

  @PostMapping("/add-item")
  public ResponseEntity<BaseResponse<String>> addItemToCart(@RequestBody AddItemToCart request) {
    try {
      
      BaseResponse<String> response = createCartService.addItemToCart(request);
      
      if (response.getCode() != 2001) {
        return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(response);
      }

      return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(response);
    } catch (Exception exception) {
      exception.printStackTrace();
      String error = ErrorMessage.getErrorMessage(exception);
      BaseResponse<String> errorResponse = new BaseResponse<String>(5000, error, null);
      return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(errorResponse);

    }
  }
}
