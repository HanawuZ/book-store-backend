package com.backend.app.boostrapper.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.backend.app.userservice.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import com.backend.app.boostrapper.config.middleware.JwtAuthenticationFilter;
import com.backend.app.boostrapper.config.middleware.RequestFilter;
import com.backend.app.shared.libraries.security.authenticator.GoogleAuthenticatorService;

@Configuration
@EnableWebSecurity
public class AuthenticationConfig {

    private RedisTemplate<Object, Object> redisTemplate;

    public AuthenticationConfig(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
        HttpSecurity http
    ) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                // // .oauth2Login(oauth -> oauth
                // //     .clientRegistrationRepository(clientRegistrationRepository())
                // //     .successHandler(null)
                // //     .failureHandler(null)
                // // )
                .authenticationProvider(authProvider())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    // @Bean
    // @Order(2)
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     return http
    //      .authorizeHttpRequests(auth -> auth.requestMatchers("/authorized").authenticated())
    // }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(
                OAuthConfig.googleClientRegistration());
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserService userService() {
        return new UserService(this.redisTemplate);
    }

    @Bean
    public GoogleAuthenticatorService googleAuthenticatorService() {
        return new GoogleAuthenticatorService();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService());
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }
}
