package com.mdm.equipmentservice.jwt;

import com.mdm.equipmentservice.response.GenericResponse;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<GenericResponse<AuthenticationResponse>> login(LoginForm loginForm) {
        return ResponseEntity.ok(new GenericResponse<>(authenticationService.authenticate(loginForm)));
    }

    @PostMapping(value = "/refresh-token")
    public ResponseEntity<GenericResponse<AuthenticationResponse>> refreshToken(HttpServletRequest request) {
        return ResponseEntity.ok(new GenericResponse<>(authenticationService.refreshToken(request)));
    }
}