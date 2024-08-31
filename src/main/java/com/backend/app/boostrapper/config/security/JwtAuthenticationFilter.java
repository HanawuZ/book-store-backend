package com.backend.app.boostrapper.config.security;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.backend.app.shared.libraries.jwt.JwtUtility;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtUtility jwtUtility = new JwtUtility();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Get authorization header and validate
        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || authorizationHeader.isEmpty()
                || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get jwt token and validate
        final String token = authorizationHeader.split(" ")[1].trim();
        if (!jwtUtility.validateToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user,
        //         null, user.getAuthorities());
        // authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
