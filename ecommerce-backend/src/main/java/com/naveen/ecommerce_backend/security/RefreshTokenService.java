package com.naveen.ecommerce_backend.security;


import com.naveen.ecommerce_backend.model.user.User;
import com.naveen.ecommerce_backend.repository.RefreshTokenRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepo refreshTokenRepo;

    @Value("${jwt.refresh-token-expiration}")
    private Long refreshTokenDuration;

    public RefreshToken createRefreshToken(User user) {

        RefreshToken refreshToken = refreshTokenRepo.findByUser(user)
                .orElse(new RefreshToken());

        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDuration));
        refreshToken.setToken(UUID.randomUUID().toString());

        return refreshTokenRepo.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken) {

        if(refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {

            refreshTokenRepo.deleteByUser(refreshToken.getUser());

            throw new RuntimeException("Refresh token expired. Please login again.");
        }

        return refreshToken;
    }
}
