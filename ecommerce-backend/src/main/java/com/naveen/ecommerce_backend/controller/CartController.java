package com.naveen.ecommerce_backend.controller;

import com.naveen.ecommerce_backend.dto.cart.CartRequest;
import com.naveen.ecommerce_backend.dto.cart.CartResponse;
import com.naveen.ecommerce_backend.service.CartService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartResponse> addItemToCart(@RequestBody CartRequest cartRequest, Authentication authentication) {

        return ResponseEntity.ok(cartService.addItemToCart(cartRequest, authentication));
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCart(Authentication authentication) {

        return ResponseEntity.ok(cartService.getCart(authentication));
    }

    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<String> removeCartItem(@PathVariable Long cartItemId) {

        return ResponseEntity.ok(cartService.removeCartItem(cartItemId));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(Authentication authentication) {

        return ResponseEntity.ok(cartService.clearCart(authentication));
    }
}
