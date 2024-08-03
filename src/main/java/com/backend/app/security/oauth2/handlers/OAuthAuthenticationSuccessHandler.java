package com.backend.app.security.oauth2.handlers;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.backend.app.security.oauth2.utils.GooglePrincipalExtractor;
import com.backend.app.models.entities.User;
import com.backend.app.security.jwt.JwtService;
import com.backend.app.security.oauth2.utils.GitHubPrincipalExtractor;
import com.backend.app.services.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;


@Component
public class OAuthAuthenticationSuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler
		implements AuthenticationSuccessHandler {
        
    private UserService userService;
    private JwtService jwtService;

    @Autowired
    public OAuthAuthenticationSuccessHandler(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
        Map<String, Object> userAttributes = oauth2Token.getPrincipal().getAttributes();
        Map<String, Object> userMap = new HashMap<>(); 
        String registrationId = oauth2Token.getAuthorizedClientRegistrationId();
        switch (registrationId) {
            case "google":
                userMap = GooglePrincipalExtractor.extract(userAttributes);
                break;
            case "github":
                userMap = GitHubPrincipalExtractor.extract(userAttributes);
                break;
            default:
                break;
        }

        // If userMap has no value
        if (userMap.isEmpty()) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create user");
            return;
        }
        
        User user = this.userService.upsertUser(userMap);
        if (user == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to create user");
            return;
        }

        String token = this.jwtService.generateToken(user);
        
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 30);
        response.addCookie(cookie);
		this.clearAuthenticationAttributes(request);
        handle(request, response, authentication);
    }

    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
	}
}
