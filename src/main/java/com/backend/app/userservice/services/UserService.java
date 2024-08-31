package com.backend.app.userservice.services;

import java.util.UUID;
import java.util.Date;

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
            User user = userRepository.findByUsername(username);
            if (user == null) {
                return null;
            }
            return user;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public BaseResponse<?> signIn(SignInRequest request) {
        try {
            User user = userRepository.findByUsername(request.getUsername());

            if (user == null) {
                return new BaseResponse(4000, "User not found", null);
            }
            return new BaseResponse(2001, "Signed in successfully", jwtUtility.generateToken(user));

        } catch (Exception e) {
            return new BaseResponse(5000, "Failed to sign in", null);
        }
    }

    public BaseResponse<?> createUserFromSignUp(SignUpRequest request) {
        try {
            User userWithExistedUsername = userRepository.findByUsername(request.getUsername());

            if (userWithExistedUsername != null) {
                return new BaseResponse(4000, "Username already exists", null);
            }

            User userWithExistedEmail = userRepository.findByEmail(request.getEmail());

            if (userWithExistedEmail != null) {
                return new BaseResponse(4000, String.format("Email %s already exists", request.getEmail()), null);
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
                return new BaseResponse(4000, "Failed to create user", null);
            }

            return new BaseResponse(2001, "Signed up successfully", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse(5000, e.getMessage(), null);
        }
    }
}
