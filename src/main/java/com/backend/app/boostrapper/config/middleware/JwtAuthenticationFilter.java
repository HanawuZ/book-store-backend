package com.backend.app.boostrapper.config.middleware;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.session.SessionManagementFilter;
import com.backend.app.userservice.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.backend.app.shared.libraries.http.BaseResponse;
import com.backend.app.shared.libraries.security.jwt.JwtUtility;
import com.backend.app.shared.models.entities.User;

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

            System.out.println("2nd Request filter, ");

            // Get authorization header and validate
            final String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader == null || authorizationHeader.isEmpty()
                    || !authorizationHeader.startsWith("Bearer ")) {
                // response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                // response.setContentType("application/json");

                // String jsonResponse = objectMapper.writeValueAsString(new
                // BaseResponse<>(4001, "Invalid Token", null));
                // response.getWriter().write(jsonResponse);

                filterChain.doFilter(request, response);
                return;

            }

            // Get jwt token and validate
            final String token = authorizationHeader.split(" ")[1].trim();
            if (jwtUtility.validateToken(token).equals(false)) {
                filterChain.doFilter(request, response);
                return;
            }

            String username = jwtUtility.extractUsername(token);
            User user = userService.loadUserByUsername(username);

            if (user == null) {
                filterChain.doFilter(request, response);
                return;
            }

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user,
                    null, user.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            String error = String.format("Internal Server Error: %s", e.getMessage());

            String jsonResponse = objectMapper.writeValueAsString(new BaseResponse<>(5000, error, null));
            response.getWriter().write(jsonResponse);
        }
    }
}
