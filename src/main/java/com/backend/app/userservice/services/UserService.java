package com.backend.app.userservice.services;

import java.util.UUID;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.app.shared.libraries.http.BaseResponse;
import com.backend.app.shared.libraries.security.authenticator.GoogleAuthenticatorService;
import com.backend.app.shared.libraries.security.jwt.JwtUtility;
import com.backend.app.shared.models.entities.User;
import com.backend.app.userservice.models.SignInRequest;
import com.backend.app.userservice.models.SignUpRequest;
import com.backend.app.userservice.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private GoogleAuthenticatorService googleAuthenticatorService;
    
    private JwtUtility jwtUtility = new JwtUtility();

    @Override
    public User loadUserByUsername(String username) {
        try {
            Optional<User> user = userRepository.findByUsername(username);
            if (user.isEmpty()) {
                return null;
            }
            return user.get();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public BaseResponse<String> signIn(SignInRequest request) {
        try {
            Optional<User> user = userRepository.findByUsername(request.getUsername());
            if (user.isEmpty()) {
                return new BaseResponse<>(4000, "User not found", null);
            }

            if (user.get().getIsUsing2FA().equals(true)) {
                String redirectUrl = String.format("https://example.com/users/verify?userId=%s", user.get().getId());
                return new BaseResponse<>(2001, "Please verify code at api verify", redirectUrl);
            }

            return new BaseResponse<>(2001, "Signed in successfully", jwtUtility.generateToken(user.get()));

        } catch (Exception e) {
            return new BaseResponse<>(5000, "Failed to sign in", null);
        }
    }

    public BaseResponse<String> createUserFromSignUp(SignUpRequest request) {
        try {
            Optional<User> userWithExistedUsername = userRepository.findByUsername(request.getUsername());

            if (userWithExistedUsername.isPresent()) {
                return new BaseResponse<>(4000, "Username already exists", null);
            }

            Optional<User> userWithExistedEmail = userRepository.findByEmail(request.getEmail());

            if (userWithExistedEmail.isPresent()) {
                return new BaseResponse<>(4000, String.format("Email %s already exists", request.getEmail()), null);
            }

            User newUser = new User();
            newUser.setId(UUID.randomUUID().toString());
            newUser.setUsername(request.getUsername());
            newUser.setFirstName(request.getFirstname());
            newUser.setLastName(request.getLastname() == null ? "" : request.getLastname());
            newUser.setEmail(request.getEmail());
            newUser.setAccountNonExpired(true);
            newUser.setAccountNonLocked(true);
            newUser.setEnabled(true);
            newUser.setCredentialsNonExpired(true);
            newUser.setIsUsing2FA(false);
            newUser.setCreatedDate(new Date());
            newUser.setPassword(this.passwordEncoder.encode(request.getPassword()));
            newUser.setSecret(googleAuthenticatorService.generateSecretKey());
            // Save user
            User savedUser = userRepository.save(newUser);
            if (savedUser == null) {
                return new BaseResponse<>(4000, "Failed to create user", null);
            }

            return new BaseResponse<>(2001, "Signed up successfully", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(5000, e.getMessage(), null);
        }
    }
}
