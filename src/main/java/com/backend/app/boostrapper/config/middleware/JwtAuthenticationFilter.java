package com.backend.app.boostrapper.config.middleware;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.backend.app.userservice.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.backend.app.shared.libraries.http.BaseResponse;
import com.backend.app.shared.libraries.security.jwt.JwtUtility;
import com.backend.app.shared.models.entities.User;

import io.jsonwebtoken.security.SignatureException;

@Order(2)
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtUtility jwtUtility = new JwtUtility();
    private ObjectMapper objectMapper = new ObjectMapper();
    private UserService userService;

    @Autowired
    public JwtAuthenticationFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            System.out.println("2nd Request filter");
            // Get authorization header and validate

            final String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader == null || authorizationHeader.isEmpty()
                    || !authorizationHeader.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");

                BaseResponse<String> responseObj = new BaseResponse<>(4001, "Unauthorized, no token provided", null);
                String jsonResponse = objectMapper.writeValueAsString(responseObj);
                response.getWriter().write(jsonResponse);
                return;
            }

            // Get jwt token and validate
            final String token = authorizationHeader.split(" ")[1].trim();
            if (!jwtUtility.validateToken(token)) {

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");

                BaseResponse<String> responseObj = new BaseResponse<>(4001, "Unauthorized, invalid token", null);
                String jsonResponse = objectMapper.writeValueAsString(responseObj);
                response.getWriter().write(jsonResponse);
                return;
            }
            String username = jwtUtility.extractUsername(token);
            if (username == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");

                BaseResponse<String> responseObj = new BaseResponse<>(4001, "Unauthorized, invalid token", null);
                String jsonResponse = objectMapper.writeValueAsString(responseObj);
                // Write JSON to response
                response.getWriter().write(jsonResponse);
                return;
            }

            User user = userService.loadUserByUsername(username);

            if (user == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");

                BaseResponse<String> responseObj = new BaseResponse<>(4001, "Unauthorized, invalid user", null);
                String jsonResponse = objectMapper.writeValueAsString(responseObj);
                // Write JSON to response
                response.getWriter().write(jsonResponse);
                return;
            }

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user,
                    null, user.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            filterChain.doFilter(request, response);
        } catch (Exception e) {

            e.printStackTrace();
            // Optionally handle unexpected exceptions and return a generic error response
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            BaseResponse<String> responseObj = new BaseResponse<>(5000, e.getMessage(), null);
            String jsonResponse = objectMapper.writeValueAsString(responseObj);
            // Write JSON to response
            response.getWriter().write(jsonResponse);
        }
    }
}
