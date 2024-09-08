package com.backend.app.userservice.services;

import java.util.UUID;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.backend.app.shared.libraries.http.BaseResponse;
import com.backend.app.shared.libraries.redis.RedisValueUtility;
import com.backend.app.shared.libraries.security.authenticator.GoogleAuthenticatorService;
import com.backend.app.shared.libraries.security.jwt.JwtUtility;
import com.backend.app.shared.models.entities.Customer;
import com.backend.app.shared.models.entities.User;
import com.backend.app.shared.models.entities.UserMapping;
import com.backend.app.userservice.models.SignInRequest;
import com.backend.app.userservice.models.SignUpRequest;
import com.backend.app.userservice.repositories.UserRepository;
import com.backend.app.userservice.services.interfaces.UserServiceInterface;

@Service
public class UserService implements UserDetailsService, UserServiceInterface {

    private static final Integer MAX_LOGIN_ATTEMPT_LIMIT = 5;

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private GoogleAuthenticatorService googleAuthenticatorService;
    private RedisValueUtility redisValueUtility;
    private JwtUtility jwtUtility;

    @Autowired
    public UserService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        GoogleAuthenticatorService googleAuthenticatorService,
        RedisValueUtility redisValueUtility,
        JwtUtility jwtUtility
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.googleAuthenticatorService = googleAuthenticatorService;
        this.redisValueUtility = redisValueUtility;
        this.jwtUtility = jwtUtility;
    }

    @Override
    public User loadUserByUsername(String username) {
        try {
            Optional<User> user = userRepository.findByUsernameOrEmail(username, username);
            if (user.isEmpty()) {
                return null;
            }
            return user.get();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public BaseResponse<String> signIn(SignInRequest request) {
        try {
            Optional<User> user = userRepository.findByUsernameOrEmail(request.getUsername(), request.getUsername());
            if (user.isEmpty()) {
                return new BaseResponse<>(4000, "User not found", null);
            }

            // Check if password is matched
            // Should blocked request from IP
            if (!passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {

                String key = String.format("attempt_%s", user.get().getId());
                String count = redisValueUtility.getValue(key);

                if (count == null) {
                    count = "1";
                    redisValueUtility.setValue(key, count, 5, TimeUnit.MINUTES);
                } else {
                    Integer newCount = Integer.parseInt(count) + 1;
                    if (newCount > MAX_LOGIN_ATTEMPT_LIMIT) {
                        return new BaseResponse<>(4290, "Too many login attempts", null);
                    }
                    redisValueUtility.setValue(key, String.valueOf(newCount), 5, TimeUnit.MINUTES);
                }
                return new BaseResponse<>(4001, "Invalid password!", null);
            }

            if (user.get().getIsUsing2FA().equals(true)) {
                String redirectUrl = String.format("https://example.com/users/verify?userId=%s", user.get().getId());
                return new BaseResponse<>(2001, "Please verify code at api verify", redirectUrl);
            }

            return new BaseResponse<>(2001, "Signed in successfully", jwtUtility.generateToken(user.get()));

        } catch (Exception exception) {
            exception.printStackTrace();
            String error = String.format("Internal server error: %s", exception.getMessage());
            return new BaseResponse<>(5000, error, null);
        }
    }

    @Override
    public BaseResponse<String> createUserFromSignUp(SignUpRequest request) {
        try {
            Optional<User> existedUser = userRepository.findByUsernameOrEmail(request.getUsername(), request.getEmail());

            if (existedUser.isPresent()) {
                return new BaseResponse<>(4000, "User with username or email already exists", null);
            }

            Date createdDate = new Date();
            User newUser = new User();

            newUser.setId(UUID.randomUUID().toString());
            newUser.setUsername(request.getUsername());
            newUser.setEmail(request.getEmail());
            newUser.setAccountNonExpired(true);
            newUser.setAccountNonLocked(true);
            newUser.setEnabled(true);
            newUser.setCredentialsNonExpired(true);
            newUser.setIsUsing2FA(false);
            newUser.setCreatedDate(createdDate);
            newUser.setPassword(this.passwordEncoder.encode(request.getPassword()));
            newUser.setSecret(googleAuthenticatorService.generateSecretKey());

            Customer customer = new Customer();
            customer.setId(UUID.randomUUID().toString());
            customer.setFirstName(request.getFirstname());
            customer.setLastName(request.getLastname() == null ? "" : request.getLastname());
            customer.setCreatedDate(createdDate);
            customer.setCreatedBy("system");
            customer.setUpdatedDate(createdDate);
            customer.setUpdatedBy("system");

            UserMapping userMapping = new UserMapping();
            userMapping.setId(UUID.randomUUID().toString());
            userMapping.setUser(newUser);
            userMapping.setAuthor(null);
            userMapping.setCustomer(customer);
            userMapping.setIsActive(true);

            // Save user
            Boolean completed = userRepository.createCustomer(newUser, customer, userMapping);
            if (completed.equals(false)) {
                return new BaseResponse<>(4000, "Failed to create user", null);
            }

            return new BaseResponse<>(2001, "Signed up successfully", null);
        } catch (Exception exception) {
            exception.printStackTrace();
            String error = String.format("Internal server error: %s", exception.getMessage());
            return new BaseResponse<>(5000, error, null);
        }
    }
}
