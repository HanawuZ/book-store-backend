package com.backend.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.app.models.entities.User;
import com.backend.app.models.requests.SignUpRequest;
import com.backend.app.models.response.BaseResponse;
import com.backend.app.services.UserService;
import com.backend.app.utils.validators.PatternMatch;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;
@RestController
@CrossOrigin
@RequestMapping("/api/v1/users")
public class UserController {
    
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getUserByUsername(@RequestParam(name = "username") String username)  {
        try {
            User user = this.userService.loadUserByUsername(username);
            if (user == null) {
                // Send bad request and message
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("User not found");
            }
    
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
    
    @PostMapping("/signup")
    public ResponseEntity<?> userSignUp(@RequestBody SignUpRequest request) {
        try {
            if (request.getUsername() == null || request.getUsername().isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Username is required");
            }
            if (request.getEmail() == null || request.getEmail().isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Email is required");
            }
            if (!PatternMatch.validEmail(request.getEmail())) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Invalid email");
            }
            if (request.getFirstname() == null || request.getFirstname().isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Firstname is required");
            }

            if (request.getPassword() == null || request.getPassword().isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Password is required");
            }
            BaseResponse response = this.userService.createUserFromSignUp(request);

            if (response.getCode() != 2001) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(response);
            }
            return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(response);
        } catch (Exception e) {
            String error = String.format("Internal server error: %s", e.getMessage());
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(error);
        }
    }
}