package com.backend.app.bootstrapper.check;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @Import(UserControllerTestConfig.class)
@SpringBootTest
@AutoConfigureMockMvc
class CheckControllerTest {
    
    private MockMvc mockMvc;
    
    @Autowired
    public CheckControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void testUnauthorizedRequest() throws Exception {
        System.out.println("Hello Test !!!");
        ResultActions result = mockMvc.perform(
            get("/authorized")
        );
        
        // Get access denied
        System.out.println(result.andReturn().getResponse().getContentAsString());
        result.andExpect(status().isUnauthorized());
    }
}
