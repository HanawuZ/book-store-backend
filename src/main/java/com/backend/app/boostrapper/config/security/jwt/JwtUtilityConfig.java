package com.backend.app.boostrapper.config.security.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.backend.app.shared.libraries.security.jwt.JwtUtility;

@Configuration
public class JwtUtilityConfig {
    
    @Bean
    public JwtUtility jwtUtility() {
        return new JwtUtility();
    }
}
