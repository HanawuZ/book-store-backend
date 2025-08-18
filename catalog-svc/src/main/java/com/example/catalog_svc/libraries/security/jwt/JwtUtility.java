package com.example.catalog_svc.libraries.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class JwtUtility {

    @Value("${security.jwt.secret}")
    private String secretKey;

    public boolean validateToken(String token) {
        System.out.println("secretKey" + secretKey);
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWT.require(algorithm).build().verify(token);
            return true;

        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException(exception.getMessage());
        }
    }

    public String getUniqueName(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                .build()
                .verify(token)
                .getClaims()
                .get("unique_name").asString();

        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException(exception.getMessage());
        }
    }
}
