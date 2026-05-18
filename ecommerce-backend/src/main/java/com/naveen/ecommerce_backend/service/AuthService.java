package com.naveen.ecommerce_backend.service;

import com.naveen.ecommerce_backend.dto.User.AuthResponse;
import com.naveen.ecommerce_backend.dto.User.LoginRequest;
import com.naveen.ecommerce_backend.dto.User.RegisterRequest;
import com.naveen.ecommerce_backend.model.Role;
import com.naveen.ecommerce_backend.model.User;
import com.naveen.ecommerce_backend.repository.UserRepo;
import com.naveen.ecommerce_backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepo userRepo;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final JwtService jwtService;

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

        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token);
    }



}
