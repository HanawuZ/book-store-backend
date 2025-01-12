package com.example.catalog_svc.app.cart.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AddItemToCart {

  @JsonProperty("bookId")
  private String bookId;

  @JsonProperty("customerId")
  private String customerId;
  
  @JsonProperty("quantity")
  private Integer quantity;
}
