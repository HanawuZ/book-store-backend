package com.backend.app.userservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SignUpRequest {
     @JsonProperty("username")
    private String username;

    @JsonProperty("firstname")
    private String firstname;

    @JsonProperty("lastname")
    private String lastname;

    @JsonProperty("password")
    private String password;

    @JsonProperty("email")
    private String email;
}
