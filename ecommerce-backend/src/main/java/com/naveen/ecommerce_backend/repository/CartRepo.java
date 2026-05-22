package com.naveen.ecommerce_backend.repository;

import com.naveen.ecommerce_backend.model.Cart.Cart;
import com.naveen.ecommerce_backend.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);
}
