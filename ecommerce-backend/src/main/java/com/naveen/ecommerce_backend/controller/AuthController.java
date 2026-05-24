package com.naveen.ecommerce_backend.controller;

import com.naveen.ecommerce_backend.dto.user.AuthResponse;
import com.naveen.ecommerce_backend.dto.user.LoginRequest;
import com.naveen.ecommerce_backend.dto.user.RefreshTokenRequest;
import com.naveen.ecommerce_backend.dto.user.RegisterRequest;
import com.naveen.ecommerce_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(Principal principal) {

        return ResponseEntity.ok(authService.logout(principal));
    }
}
