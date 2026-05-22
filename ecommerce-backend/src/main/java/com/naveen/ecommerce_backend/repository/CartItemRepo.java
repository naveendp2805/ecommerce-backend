package com.naveen.ecommerce_backend.repository;

import com.naveen.ecommerce_backend.model.Cart.Cart;
import com.naveen.ecommerce_backend.model.Cart.CartItem;
import com.naveen.ecommerce_backend.model.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    List<CartItem> findByCart(Cart cart);
}
