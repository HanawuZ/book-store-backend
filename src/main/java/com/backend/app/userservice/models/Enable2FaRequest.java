package com.backend.app.userservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Enable2FaRequest {
    
    @JsonProperty("userId")
    private String userId;

    @JsonProperty("totpCode")
    private String totpCode;
}
