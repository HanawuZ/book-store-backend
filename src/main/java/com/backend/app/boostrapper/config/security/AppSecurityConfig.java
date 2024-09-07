package com.backend.app.boostrapper.config.security;

import com.backend.app.boostrapper.config.middleware.HttpRequestFilter;
import com.backend.app.boostrapper.config.middleware.JwtAuthenticationFilter;
import com.backend.app.userservice.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
@EnableWebSecurity(debug = true)
@Configuration
public class AppSecurityConfig {

    private HttpRequestFilter httpRequestFilter;
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public AppSecurityConfig(UserService userService) {
        this.httpRequestFilter = new HttpRequestFilter();
        this.jwtAuthenticationFilter = new JwtAuthenticationFilter(userService);
    }

    /**
     * Security filter chain configuration for all incoming requests.
     *
     */
    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .logout(logout -> logout.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/authorized").authenticated())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .addFilterAfter(httpRequestFilter, CsrfFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, AuthorizationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
 
    private HttpSecurity disableFilter(HttpSecurity http) {
        return null;
    }
}
