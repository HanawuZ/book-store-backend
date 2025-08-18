package com.example.catalog_svc.configs.security;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.catalog_svc.libraries.security.jwt.JwtUtility;
import com.example.catalog_svc.models.response.BaseResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtility jwtUtility;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.equals("/health") || path.startsWith("/api/v1/books");
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {

            System.out.println("DO FILTER INTERNAL");
            var authHeader = request.getHeader("Authorization");
            if (authHeader == null) {
                throw new ServletException("Authorization header not found");
            }

            String token = authHeader.replace("Bearer ", "");
            if (token.isEmpty()) {
                throw new ServletException("Token not found");
            }

            boolean isValid = jwtUtility.validateToken(token);

            if (isValid) {
                String uniqueName = jwtUtility.getUniqueName(token);

                System.out.println("uniqueName: " + uniqueName);

                // 🔹 Give default role
                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(uniqueName,
                        null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            exception.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            String error = String.format("Unauthorized: %s", exception.getMessage());

            String jsonResponse = objectMapper.writeValueAsString(new BaseResponse<>(5000, error, null));
            response.getWriter().write(jsonResponse);

        }

    }
}
