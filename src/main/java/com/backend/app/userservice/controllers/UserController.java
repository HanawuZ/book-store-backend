package com.backend.app.userservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;

import com.backend.app.userservice.models.SignInRequest;
import com.backend.app.userservice.models.SignUpRequest;
import com.backend.app.userservice.services.UserService;
import com.backend.app.shared.libraries.validator.PatternMatch;
import com.backend.app.shared.libraries.http.BaseResponse;

@RestController
@RequestMapping("api/v1/users")
@CrossOrigin
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> userSignIn(@RequestBody SignInRequest request) {
        try {
            if (request.getUsername() == null || request.getUsername().isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Username is required");
            }

            if (request.getPassword() == null || request.getPassword().isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Password is required");
            }

            BaseResponse response = this.userService.signIn(request);

            if (response.getCode() != 2001) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Invalid username or password");
            }

            return ResponseEntity.status(HttpServletResponse.SC_OK).body(response);

        } catch (Exception e) {
            String error = String.format("Internal server error: %s", e.getMessage());
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(error);
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
            if (PatternMatch.isEmailValid(request.getEmail()).equals(false)) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Invalid email");
            }
            if (request.getFirstname() == null || request.getFirstname().isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Firstname is required");
            }

            if (request.getPassword() == null || request.getPassword().isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Password is required");
            }
            BaseResponse response = userService.createUserFromSignUp(request);

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
