package com.backend.app.shared.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CheckRequest {
    
    @JsonProperty("text")
    private String text;
}
