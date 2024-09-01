package com.backend.app.boostrapper.config.middleware;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import com.backend.app.shared.libraries.http.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Order(1)
@Component
public class RequestFilter extends OncePerRequestFilter {

    // Create POST, PUT, PATCH string array
    private static final List<String> CONTENT_ALLOWED_STRINGS = Arrays.asList("POST", "PUT", "PATCH");

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            
            String method = request.getMethod();
            String contentType = request.getContentType();
            
            System.out.println("1st Request filter, "+ contentType);
            if (CONTENT_ALLOWED_STRINGS.contains(method)) {

                // get content type
                if (contentType == null || !contentType.equals("application/json")) {
                    response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
                    response.setContentType("application/json");

                    BaseResponse<String> responseObj = new BaseResponse<>(4150, "Unsupported Media Type", null);
                    String jsonResponse = objectMapper.writeValueAsString(responseObj);
                    // Write JSON to response
                    response.getWriter().write(jsonResponse);

                    return;
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
