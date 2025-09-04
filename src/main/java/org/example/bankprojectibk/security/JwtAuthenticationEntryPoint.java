package org.example.bankprojectibk.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        response.getWriter().write("""
            {
              "timestamp": "%s",
              "status": 401,
              "error": "Unauthorized",
              "message": "Your token is invalid or expired. Please login again.",
              "path": "%s"
            }
            """.formatted(java.time.OffsetDateTime.now(), request.getRequestURI()));
    }
}
