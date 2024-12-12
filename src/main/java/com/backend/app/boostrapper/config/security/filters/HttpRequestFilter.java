package com.backend.app.boostrapper.config.security.filters;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

import com.backend.app.shared.error.ErrorMessage;
import com.backend.app.shared.libraries.http.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class HttpRequestFilter extends OncePerRequestFilter {

  // Create POST, PUT, PATCH string array
  private static final List<String> CONTENT_ALLOWED_STRINGS = Arrays.asList("POST", "PUT", "PATCH");

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {

      String method = request.getMethod();
      String contentType = request.getContentType();

      System.out.println("1st Request filter, " + contentType);
      if (CONTENT_ALLOWED_STRINGS.contains(method)) {

        // Get api request endpoint
        String endpoint = request.getRequestURI();
        System.out.println("Endpoint: " + endpoint);

        if (endpoint == "/api/v1/books/upload") {
          if (contentType == null || !contentType.equals("multipart/form-data")) {
            response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
            response.setContentType("application/json");

            BaseResponse<String> responseObj = new BaseResponse<>(4150, "Unsupported Media Type", null);
            String jsonResponse = objectMapper.writeValueAsString(responseObj);
            // Write JSON to response
            response.getWriter().write(jsonResponse);

            return;
          }

        }

        // get content type
        // if (contentType == null || !contentType.equals("application/json")) {
        // response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        // response.setContentType("application/json");

        // BaseResponse<String> responseObj = new BaseResponse<>(4150, "Unsupported
        // Media Type", null);
        // String jsonResponse = objectMapper.writeValueAsString(responseObj);
        // // Write JSON to response
        // response.getWriter().write(jsonResponse);

        // return;
        // }
      }

      filterChain.doFilter(request, response);
    } catch (Exception exception) {
      exception.printStackTrace();
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      response.setContentType("application/json");

      String error = ErrorMessage.getErrorMessage(exception);
      BaseResponse<String> responseObj = new BaseResponse<>(5000, error, null);
      String jsonResponse = objectMapper.writeValueAsString(responseObj);
      // Write JSON to response
      response.getWriter().write(jsonResponse);

    }
  }
}
