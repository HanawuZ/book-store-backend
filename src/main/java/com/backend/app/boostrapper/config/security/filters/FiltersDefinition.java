package com.backend.app.boostrapper.config.security.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.backend.app.userservice.services.UserService;

@Configuration
public class FiltersDefinition {
    
    private UserService userService;

    @Autowired
    public FiltersDefinition(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public HttpRequestFilter httpRequestFilter() {
        return new HttpRequestFilter();
    }

    @Bean JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(userService);
    }
}
