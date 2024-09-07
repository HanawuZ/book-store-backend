package com.backend.app.catalogservicetest.services.cart;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import com.backend.app.catalogservice.repositories.CartRepository;
import com.backend.app.catalogservice.services.ShoppingCartService;

@TestConfiguration
public class ShoppingCartServiceTestConfig {
 
    @MockBean
    private CartRepository cartRepository;

    @Bean
    public ShoppingCartService shoppingCartService() {
        return new ShoppingCartService(cartRepository);
    }
}
