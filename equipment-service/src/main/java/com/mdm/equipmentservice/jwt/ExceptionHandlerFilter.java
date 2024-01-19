package com.mdm.equipmentservice.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdm.equipmentservice.response.ErrorResponse;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtException exception) {
            //Ex: split uri /api/v1/auth/refresh-token => String[] = {"api", "v1", "auth", "refresh-token"}
            String[] pathPart = request.getRequestURI().split("/");
            ErrorResponse errorResponse = getTokenExpiredResponse(request, exception, pathPart);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
        }
    }

    private static ErrorResponse getTokenExpiredResponse(HttpServletRequest request, JwtException e, String[] pathPart) {
        String endpoint = pathPart[pathPart.length - 1];
        String tokenType;
        if (endpoint.equals("refresh-token")) {
            tokenType = "refresh-token";
        } else {
            tokenType = "access-token";
        }
        ErrorResponse errorResponse = new ErrorResponse(
                Collections.singletonList(e.getLocalizedMessage()),
                e.getMessage(),
                HttpStatus.UNAUTHORIZED.value(),
                request.getRequestURI()
        );
        errorResponse.setData(tokenType);
        return errorResponse;
    }
}
