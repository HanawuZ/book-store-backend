package com.backend.app.userservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.app.userservice.models.Enable2FaRequest;
import com.backend.app.userservice.services.AuthenticationService;

import jakarta.servlet.http.HttpServletResponse;

import com.backend.app.shared.libraries.http.BaseResponse;
@RestController
@RequestMapping("api/v1/users")
@CrossOrigin
public class AuthenticationController {
    
    private AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/2fa/qr-code")
    public ResponseEntity<BaseResponse<String>> generateQrCode(@RequestParam("id") String id) {
        try {
            if (id == null || id.isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new BaseResponse<String>(4000, "User id is required", null));
            }

            BaseResponse<String> response = this.authenticationService.generateQrCode(id);
            if (response.getCode() != 2000) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(response);
            }
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            String error = String.format("Internal server error: %s", e.getMessage());
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(new BaseResponse<String>(5000, error, null));
        }
    }

    @PatchMapping("/2fa/enable") 
    public ResponseEntity<BaseResponse<String>> enable2FA(@RequestBody Enable2FaRequest request) {
        try {
            if (request.getUserId() == null || request.getUserId().isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new BaseResponse<String>(4000, "User id is required", null));
            }
            if (request.getTotpCode() == null) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new BaseResponse<String>(4000, "Totp code is required", null));
            }

            BaseResponse<String> response = this.authenticationService.enable2FA(request);
            if (response.getCode() != 2000) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(response);
            }
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(response);

        } catch (Exception e) {
            e.printStackTrace();
            String error = String.format("Internal server error: %s", e.getMessage());
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(new BaseResponse<String>(5000, error, null));
        }
    }
    
    @PostMapping("/2fa/verify")
    public ResponseEntity<BaseResponse<String>> verify2FA(@RequestBody Enable2FaRequest request) {
        try {
            if (request.getUserId() == null || request.getUserId().isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new BaseResponse<String>(4000, "User id is required", null));
            }
            if (request.getTotpCode() == null || request.getTotpCode().isEmpty()) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new BaseResponse<String>(4000, "OTP is required", null));
            }

            BaseResponse<String> response = this.authenticationService.verify2FA(request);
            if (response.getCode() != 2000) {
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(response);
            }
            return ResponseEntity.status(HttpServletResponse.SC_OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            String error = String.format("Internal server error: %s", e.getMessage());
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body(new BaseResponse<String>(5000, error, null));
        }
    } 
}
