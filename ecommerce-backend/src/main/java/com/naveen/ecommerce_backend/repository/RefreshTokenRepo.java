package com.naveen.ecommerce_backend.repository;

import com.naveen.ecommerce_backend.security.RefreshToken;
import com.naveen.ecommerce_backend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    List<RefreshToken> findByUser(User user);

    void deleteByUser(User user);
}
