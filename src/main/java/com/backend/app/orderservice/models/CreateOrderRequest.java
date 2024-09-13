package com.backend.app.orderservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CreateOrderRequest {

    @JsonProperty("customerId")
    private String customerId;
    
    @JsonProperty("customerAddresssId")
    private String customerAddresssId;

    @JsonProperty("note")
    private String note;
}
