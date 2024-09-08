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
    public ResponseEntity<BaseResponse<String>> userSignIn(@RequestBody SignInRequest request) {
        try {
            if (request.getUsername() == null || request.getUsername().isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
                        .body(new BaseResponse<String>(4000, "Username is required", null));
            }

            if (request.getPassword() == null || request.getPassword().isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
                        .body(new BaseResponse<String>(4000, "Password is required", null));
            }

            BaseResponse<String> response = userService.signIn(request);
            
            if (response.getCode().equals(5000)) {
                return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(response);
            }
            if (response.getCode().equals(4001)) {
                return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body(response);
            }
            if (response.getCode() != 2001) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(response);
            }

            return ResponseEntity.status(HttpServletResponse.SC_OK).body(response);

        } catch (Exception exception) {
            exception.printStackTrace();
            String error = String.format("Internal server error: %s", exception.getMessage());
            BaseResponse<String> errorResponse = new BaseResponse<>(5000, error, null);
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<String>> userSignUp(@RequestBody SignUpRequest request) {
        try {
            if (request.getUsername() == null || request.getUsername().isEmpty()) {
                return ResponseEntity
                        .status(HttpServletResponse.SC_BAD_REQUEST)
                        .body(new BaseResponse<String>(4000, "Username is required", null));
            }
            if (request.getEmail() == null || request.getEmail().isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
                        .body(new BaseResponse<String>(4000, "Email is required", null));
            }
            if (PatternMatch.isEmailValid(request.getEmail()).equals(false)) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
                        .body(new BaseResponse<String>(4000, "Email is not valid", null));
            }
            if (request.getFirstname() == null || request.getFirstname().isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
                        .body(new BaseResponse<String>(4000, "Firstname is required", null));
            }

            if (request.getPassword() == null || request.getPassword().isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST)
                        .body(new BaseResponse<String>(4000, "Password is required", null));
            }
            BaseResponse<String> response = userService.createUserFromSignUp(request);

            if (response.getCode() != 2001) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(response);
            }
            return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(response);
        } catch (Exception exception) {
            exception.printStackTrace();
            String error = String.format("Internal server error: %s", exception.getMessage());
            BaseResponse<String> errorResponse = new BaseResponse<>(5000, error, null);
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
