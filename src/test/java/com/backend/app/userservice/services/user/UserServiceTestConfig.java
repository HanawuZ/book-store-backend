package com.backend.app.userservice.services.user;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import com.backend.app.userservice.repositories.UserRepository;
import com.backend.app.userservice.services.UserService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import com.backend.app.shared.libraries.redis.MockRedisValueUtility;
import com.backend.app.shared.libraries.redis.RedisValueUtility;
import com.backend.app.shared.libraries.security.authenticator.GoogleAuthenticatorService;
import com.backend.app.shared.libraries.security.authenticator.MockGoogleAuthenticatorService;
import com.backend.app.shared.libraries.security.jwt.JwtUtility;
import com.backend.app.shared.libraries.security.jwt.MockJwtUtility;

@TestConfiguration
class UserServiceTestConfig {

    @MockBean
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private RedisValueUtility redisValueUtility = new MockRedisValueUtility();

    private JwtUtility jwtUtility = new MockJwtUtility();

    private GoogleAuthenticatorService googleAuthenticatorService = new MockGoogleAuthenticatorService();

    @Bean
    public UserService userService() {
        return new UserService(userRepository, passwordEncoder, googleAuthenticatorService, redisValueUtility, jwtUtility);
    }
}
