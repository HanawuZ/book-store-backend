package com.backend.app.userservice.controllers.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import com.backend.app.shared.libraries.http.BaseResponse;
import com.backend.app.userservice.models.SignInRequest;
import com.backend.app.userservice.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Import(UserControllerTestConfig.class)
@AutoConfigureMockMvc
class UserControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public UserControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void testSignIn() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setUsername("test");
        signInRequest.setPassword("test");

        ResultActions result = mockMvc.perform(
            post("/api/v1/users/signin")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signInRequest))
        );

        result.andExpect(status().isOk());
        result.andExpect(content().json(objectMapper.writeValueAsString(new BaseResponse<>(2001, "Signed in successfully", "token"))));
    }

    @Test
    void signInFailed_MissingPassword() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setUsername("test");

        ResultActions result = mockMvc.perform(
            post("/api/v1/users/signin")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signInRequest))
        );

        result.andExpect(status().isBadRequest());
        result.andExpect(content().string("Password is required"));
    }

    @Test
    void signInFailed_MissingUsername() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setPassword("test");

        ResultActions result = mockMvc.perform(
            post("/api/v1/users/signin")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signInRequest))
        );

        result.andExpect(status().isBadRequest());
        result.andExpect(content().string("Username is required"));
    }
}
