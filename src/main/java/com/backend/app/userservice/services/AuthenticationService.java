package com.backend.app.userservice.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.app.shared.libraries.http.BaseResponse;
import com.backend.app.shared.libraries.security.authenticator.GoogleAuthenticatorService;
import com.backend.app.shared.models.entities.User;
import com.backend.app.userservice.models.Enable2FaRequest;
import com.backend.app.userservice.repositories.UserRepository;

@Service
public class AuthenticationService {

    @Autowired
    private GoogleAuthenticatorService googleAuthenticatorService;

    @Autowired
    private UserRepository userRepository;

    public BaseResponse<String> generateQrCode(String id) {
        try {
            Optional<User> existedUser = userRepository.findById(id);
            if (existedUser.isEmpty()) {
                return new BaseResponse<>(4000, "User not found", null);
            }
            if (existedUser.get().getIsUsing2FA().equals(true)) {
                return new BaseResponse<>(4000, "User already using 2FA", null);
            }
            if (existedUser.get().getSecret() == null || existedUser.get().getSecret().isEmpty()) {
                return new BaseResponse<>(4000, "User secret is empty", null);
            }

            String qrCodeUrl = googleAuthenticatorService.generateQRUrl(existedUser.get().getSecret(), existedUser.get().getUsername());

            if (qrCodeUrl == null) {
                return new BaseResponse<>(4000, "Failed to generate QR code", null);
            }
            return new BaseResponse<>(2000, "Get QR code successfully", qrCodeUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(5000, e.getMessage(), null);
        }
    }

    public BaseResponse<String> enable2FA(Enable2FaRequest request) {
        try {

            Optional<User> existedUser = userRepository.findById(request.getUserId());
            if (existedUser.isEmpty()) {
                return new BaseResponse<>(4000, "User not found", null);
            }
            if (existedUser.get().getIsUsing2FA().equals(true)) {
                return new BaseResponse<>(4000, "User already using 2FA", null);
            }

            Boolean isVerified = googleAuthenticatorService.isValid(existedUser.get().getSecret(), request.getTotpCode());
            if (isVerified.equals(false)) {
                return new BaseResponse<>(4000, "Invalid 2FA code", null);
            }
            
            User updatedUser = existedUser.get();
            updatedUser.setIsUsing2FA(true);
            User savedUser = userRepository.save(updatedUser);
            if (savedUser == null) {
                return new BaseResponse<>(4000, "Failed to enable 2FA", null);
            }
            return new BaseResponse<>(2000, "Enable 2FA successfully", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResponse<>(5000, e.getMessage(), null);
        }
    }
}
