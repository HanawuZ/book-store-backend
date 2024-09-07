package com.backend.app.shared.libraries.security.jwt;

import com.backend.app.shared.models.entities.User;

public class MockJwtUtility extends JwtUtility {
    
    @Override
    public String generateToken(User user) {
        return "jwt-token";
    }
}
