package com.backend.app.shared.libraries.security.authenticator;

public class MockGoogleAuthenticatorService extends GoogleAuthenticatorService {
    
    @Override
    public String generateSecretKey() {
        return "secret-key";
    }

    @Override
    public boolean isValid(String secret, int code) {
        return true;
    }

    @Override
    public String generateQRUrl(String secret, String username) {
        return "data:image/png;base64," + secret;
    }

    public static String generateQRBase64(String qrCodeText) {
        return "data:image/png;base64," + qrCodeText;
    }
}
