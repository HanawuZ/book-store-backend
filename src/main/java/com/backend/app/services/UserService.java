package com.backend.app.services;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.app.models.entities.User;
import com.backend.app.models.requests.SignInRequest;
import com.backend.app.models.requests.SignUpRequest;
import com.backend.app.repositories.UserRepository;
import com.backend.app.security.jwt.JwtService;
import com.backend.app.models.response.BaseResponse;


@Service
public class UserService implements UserDetailsService {

    @Autowired private UserRepository userRepository;
    
    @Autowired private PasswordEncoder passwordEncoder;
    
    @Autowired private JwtService jwtService;

    @Override
    public User loadUserByUsername(String username) {
        try {
            Optional<User> user = userRepository.getUserByUsername(username);

            if (user.isPresent()) {
                return user.get();
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User upsertUser(Map<String, Object> userMap) {
        try {
            String userProviderId = userMap.get("providerId").toString();
            Optional<User> existedUser = userRepository.getUserByProviderId(userProviderId);
    
            User user = existedUser.orElseGet(() -> {
                User newUser = new User();
                newUser.setId(UUID.randomUUID().toString());
                newUser.setCreatedDate(new Date());
                newUser.setProvider(userMap.get("provider").toString());
                newUser.setProviderId(userProviderId);
                return newUser;
            });
    
            String fullname = (String) userMap.get("fullname");
            String[] name = fullname.split(" ");
            user.setFirstName(name[0]);
            if (name.length > 1) {
                user.setLastName(name[1]);
            }
            user.setUsername((String) userMap.get("username"));
            user.setProfilePicture((String) userMap.get("profilePicture"));
            if (userMap.get("email") != null) {
                user.setEmail((String) userMap.get("email"));
            }
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setEnabled(true);
    
            User newUser = userRepository.save(user);
            if (newUser == null) {
                return null;
            }
    
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public BaseResponse<?> createUserFromSignUp(SignUpRequest request) {
        try {
            Optional<User> userWithExistedUsername = userRepository.getUserByUsername(request.getUsername());

            if (userWithExistedUsername.isPresent()) {
                return new BaseResponse(4000, "Username already exists", null);
            }

            Optional<User> userWithExistedEmail = userRepository.getUserByEmail(request.getEmail());

            if (userWithExistedEmail.isPresent()) {
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
            newUser.setCreatedDate(new Date());
            newUser.setPassword(this.passwordEncoder.encode(request.getPassword()));

            // Save user
            User savedUser = userRepository.save(newUser);
            if (savedUser == null) {
                return new BaseResponse(4000, "Failed to create user", null);
            }

            return  new BaseResponse(2001, "Signed up successfully", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse(5000, "Failed to create user", null);
        }
    }
    
    public BaseResponse<?> signIn(SignInRequest request) {
        try {
            Optional<User> user = userRepository.getUserByUsername(request.getUsername());

            if (user.isEmpty()) {
                return new BaseResponse(4000, "User not found", null);
            }

            String accessToken = this.jwtService.generateToken(user.get());

            return new BaseResponse(2001, "Signed in successfully", accessToken);

        } catch (Exception e) {
            return new BaseResponse(5000, "Failed to sign in", null);
        }
    }
}
