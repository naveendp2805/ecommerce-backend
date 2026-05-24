package com.naveen.ecommerce_backend.service;

import com.naveen.ecommerce_backend.dto.user.AuthResponse;
import com.naveen.ecommerce_backend.dto.user.LoginRequest;
import com.naveen.ecommerce_backend.dto.user.RefreshTokenRequest;
import com.naveen.ecommerce_backend.dto.user.RegisterRequest;
import com.naveen.ecommerce_backend.exception.ResourceNotFoundException;
import com.naveen.ecommerce_backend.model.user.Role;
import com.naveen.ecommerce_backend.model.user.User;
import com.naveen.ecommerce_backend.repository.RefreshTokenRepo;
import com.naveen.ecommerce_backend.repository.UserRepo;
import com.naveen.ecommerce_backend.security.JwtService;
import com.naveen.ecommerce_backend.security.RefreshToken;
import com.naveen.ecommerce_backend.security.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepo userRepo;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final JwtService jwtService;

    private final RefreshTokenService refreshTokenService;

    private final RefreshTokenRepo refreshTokenRepo;

    public String register(RegisterRequest registerRequest) {

        User user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();

        userRepo.save(user);

        return "User registered Successfully.";
    }

    public AuthResponse login(LoginRequest loginRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

        String accessToken = jwtService.generateToken(userDetails);

        User user = userRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found!!"));

        String refreshToken = refreshTokenService.createRefreshToken(user).getToken();

        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse refreshToken(RefreshTokenRequest request) {

        String requestToken = request.getRefreshToken();

        RefreshToken oldRefreshToken = refreshTokenRepo.findByToken(requestToken)
                .map(refreshTokenService::verifyExpiration)
                .orElseThrow(() -> new ResourceNotFoundException("Refresh Token not found!!"));

        User user = oldRefreshToken.getUser();

        refreshTokenRepo.deleteByUser(user);

        String newAccessToken = jwtService.generateTokenFromUsername(user.getEmail());

        String newRefreshToken = refreshTokenService.createRefreshToken(user).getToken();

        return new AuthResponse(newAccessToken, newRefreshToken);
    }

    public String logout(Principal principal) {

        User user = userRepo.findByEmail(principal.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found!!"));

        refreshTokenRepo.deleteByUser(user);

        return "Logged out successfully!";
    }
}
