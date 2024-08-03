package com.backend.app.security.oauth2.handlers;

import java.io.IOException;
import java.util.Map;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import java.util.Collection;
import java.util.HashMap;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import com.backend.app.models.entities.User;
import com.backend.app.security.jwt.JwtService;
import com.backend.app.services.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

/* 
Authority: [OIDC_USER, SCOPE_https://www.googleapis.com/auth/userinfo.email, SCOPE_https://www.googleapis.com/auth/userinfo.profile, SCOPE_openid]
Attributes:
at_hash: re2MlIOMtV5bLF6oBQKmDQ
sub: 116260039875396674339
email_verified: true
iss: https://accounts.google.com
given_name: THANAWUT
nonce: VR10T_-_Kb_edRzDYZXmPgsFWCa2obqJJgMqtO6-_JQ
picture: https://lh3.googleusercontent.com/a/ACg8ocLNUJP-VGX9HIHPFcdLMP5xUXOt77U1F_ZjIPrKbufPZcZ-nQ=s96-c
aud: [455220157104-962k4mdltmoh4dio6dvam3dc030q1879.apps.googleusercontent.com]
azp: 455220157104-962k4mdltmoh4dio6dvam3dc030q1879.apps.googleusercontent.com
name: THANAWUT TUAMPRAJAK
exp: 2024-08-01T11:51:24Z
family_name: TUAMPRAJAK
iat: 2024-08-01T10:51:24Z
email: hanawu6302245@gmail.com
*/
@Component
public class GoogleOAuth2SuccessHandler {

    private UserService userService;
    private JwtService jwtService;

    @Autowired
    public GoogleOAuth2SuccessHandler(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    public void handle(HttpServletRequest request, HttpServletResponse response,
            OAuth2AuthenticationToken authenticationToken) throws IOException, ServletException {
        try {
             DefaultOAuth2User oauth2User = (DefaultOAuth2User) authenticationToken.getPrincipal();
            Map<String, Object> userAttributes = oauth2User.getAttributes();

            Map<String, Object> userMap = new HashMap<>();
            userMap.put("username", userAttributes.get("given_name"));
            userMap.put("providerId", userAttributes.get("sub"));
            userMap.put("provider", "Google");
            userMap.put("profilePicture", userAttributes.get("picture"));
            userMap.put("fullname", userAttributes.get("name"));
            userMap.put("email", userAttributes.get("email"));

            User user = this.userService.upsertUser(userMap);
            if (user == null) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "OAuth 2.0 authentication failed");
                return;
            }

            String token = this.jwtService.generateToken(user);
            Cookie cookie = new Cookie("ex_jwt", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            response.sendRedirect("/");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
