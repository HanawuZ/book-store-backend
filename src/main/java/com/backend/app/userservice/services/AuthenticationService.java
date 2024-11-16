package com.backend.app.userservice.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.app.shared.libraries.http.BaseResponse;
import com.backend.app.shared.libraries.security.authenticator.GoogleAuthenticatorService;
import com.backend.app.shared.libraries.security.jwt.JwtUtility;
import com.backend.app.shared.models.entities.User;
import com.backend.app.userservice.models.Enable2FaRequest;

@Service
public class AuthenticationService {

    // private GoogleAuthenticatorService googleAuthenticatorService;
    // private UserRepository userRepository;
    // private JwtUtility jwtUtility;

    // @Autowired
    // public AuthenticationService(
    //         GoogleAuthenticatorService googleAuthenticatorService,
    //         UserRepository userRepository,
    //         JwtUtility jwtUtility) {
    //     this.googleAuthenticatorService = googleAuthenticatorService;
    //     this.userRepository = userRepository;
    //     this.jwtUtility = jwtUtility;
    // }

    // public BaseResponse<String> generateQrCode(String id) {
    //     try {
    //         Optional<User> existedUser = userRepository.findById(id);
    //         if (existedUser.isEmpty()) {
    //             return new BaseResponse<>(4000, "User not found", null);
    //         }
    //         if (existedUser.get().getIsUsing2FA().equals(true)) {
    //             return new BaseResponse<>(4000, "User already using 2FA", null);
    //         }
    //         if (existedUser.get().getSecret() == null || existedUser.get().getSecret().isEmpty()) {
    //             return new BaseResponse<>(4000, "User secret is empty", null);
    //         }

    //         String qrCodeUrl = googleAuthenticatorService.generateQRUrl(existedUser.get().getSecret(),
    //                 existedUser.get().getUsername());

    //         if (qrCodeUrl == null) {
    //             return new BaseResponse<>(4000, "Failed to generate QR code", null);
    //         }
    //         return new BaseResponse<>(2000, "Get QR code successfully", qrCodeUrl);
    //     } catch (Exception exception) {
    //         exception.printStackTrace();
    //         String error = String.format("Internal server error: %s", exception.getMessage());
    //         return new BaseResponse<>(5000, error, null);
    //     }
    // }

    // public BaseResponse<String> enable2FA(Enable2FaRequest request) {
    //     try {
    //         Optional<User> existedUser = userRepository.findById(request.getUserId());
    //         if (existedUser.isEmpty()) {
    //             return new BaseResponse<>(4000, "User not found", null);
    //         }
    //         if (existedUser.get().getIsUsing2FA().equals(true)) {
    //             return new BaseResponse<>(4000, "User already using 2FA", null);
    //         }

    //         Boolean isVerified = googleAuthenticatorService.isValid(existedUser.get().getSecret(),
    //                 Integer.parseInt(request.getTotpCode()));
    //         if (isVerified.equals(false)) {
    //             return new BaseResponse<>(4000, "Invalid 2FA code", null);
    //         }

    //         User updatedUser = existedUser.get();
    //         updatedUser.setIsUsing2FA(true);
    //         Boolean completed = userRepository.updateUser(updatedUser);
    //         if (completed.equals(false)) {
    //             return new BaseResponse<>(4000, "Failed to enable 2FA", null);
    //         }
    //         return new BaseResponse<>(2000, "Enable 2FA successfully", null);
    //     } catch (Exception exception) {
    //         exception.printStackTrace();
    //         String error = String.format("Internal server error: %s", exception.getMessage());
    //         return new BaseResponse<>(5000, error, null);
    //     }
    // }

    // public BaseResponse<String> verify2FA(Enable2FaRequest request) {
    //     try {
    //         Optional<User> existedUser = userRepository.findById(request.getUserId());
    //         if (existedUser.isEmpty()) {
    //             return new BaseResponse<>(4000, "User not found", null);
    //         }

    //         Boolean isVerified = googleAuthenticatorService.isValid(existedUser.get().getSecret(),
    //                 Integer.parseInt(request.getTotpCode()));
    //         if (isVerified.equals(false)) {
    //             return new BaseResponse<>(4000, "Invalid 2FA code", null);
    //         }

    //         return new BaseResponse<>(2000, "Verified 2FA successfully", jwtUtility.generateToken(existedUser.get()));
    //     } catch (Exception exception) {
    //         exception.printStackTrace();
    //         String error = String.format("Internal server error: %s", exception.getMessage());
    //         return new BaseResponse<>(5000, error, null);
    //     }
    // }
}
