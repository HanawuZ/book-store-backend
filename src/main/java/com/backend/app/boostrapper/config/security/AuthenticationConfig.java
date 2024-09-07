package com.backend.app.boostrapper.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import com.backend.app.userservice.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import com.backend.app.shared.libraries.security.authenticator.GoogleAuthenticatorService;

@Configuration
public class AuthenticationConfig {

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(OAuthConfig.googleClientRegistration());
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public GoogleAuthenticatorService googleAuthenticatorService() {
        return new GoogleAuthenticatorService();
    }

    // @Bean
    // public DaoAuthenticationProvider authProvider() {
    //     DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    //     authProvider.setUserDetailsService(userService());
    //     authProvider.setPasswordEncoder(encoder());
    //     return authProvider;
    // }
}
