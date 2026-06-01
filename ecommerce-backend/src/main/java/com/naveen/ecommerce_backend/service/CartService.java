package com.naveen.ecommerce_backend.service;

import com.naveen.ecommerce_backend.dto.cart.CartItemResponse;
import com.naveen.ecommerce_backend.dto.cart.CartRequest;
import com.naveen.ecommerce_backend.dto.cart.CartResponse;
import com.naveen.ecommerce_backend.exception.ResourceNotFoundException;
import com.naveen.ecommerce_backend.model.cart.Cart;
import com.naveen.ecommerce_backend.model.cart.CartItem;
import com.naveen.ecommerce_backend.model.product.Product;
import com.naveen.ecommerce_backend.model.user.User;
import com.naveen.ecommerce_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepo cartRepo;

    private final CartItemRepo cartItemRepo;

    private final ProductRepo productRepo;

    private final UserRepo userRepo;

    public CartResponse addItemToCart(CartRequest cartRequest, Authentication authentication)  {

        String email = authentication.getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = cartRepo.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .user(user)
                            .build();
                    return cartRepo.save(newCart);
                });

        Product product = productRepo.findById(cartRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Optional<CartItem> existingCartItem = cartItemRepo.findByCartAndProduct(cart, product);

        if(existingCartItem.isPresent())
        {
            CartItem cartItem = existingCartItem.get();

            int qty = cartItem.getQuantity() + cartRequest.getQuantity();

            if(qty > product.getStockQuantity())
                throw new RuntimeException("Insufficient stock!!");

            cartItem.setQuantity(qty);
            cartItemRepo.save(cartItem);
        }
        else
        {
            if(cartRequest.getQuantity() > product.getStockQuantity()) throw new RuntimeException("Insufficient stock!!");

            CartItem newCartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(cartRequest.getQuantity())
                    .build();

            cartItemRepo.save(newCartItem);
        }

        return getCart(authentication);
    }

    public CartResponse getCart(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = cartRepo.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        List<CartItemResponse> items = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.valueOf(0);

        List<CartItem> cartItems = cartItemRepo.findByCart(cart);

        for(CartItem cartItem : cartItems)
        {
            BigDecimal subtotal = cartItem.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));

            totalAmount = totalAmount.add(subtotal);

            CartItemResponse cartItemResponse = CartItemResponse.builder()
                    .cartItemId(cartItem.getId())
                    .productId(cartItem.getProduct().getId())
                    .productName(cartItem.getProduct().getName())
                    .price(cartItem.getProduct().getPrice())
                    .quantity(cartItem.getQuantity())
                    .subTotal(subtotal)
                    .build();

            items.add(cartItemResponse);
        }

        return CartResponse.builder()
                .cartId(cart.getId())
                .items(items)
                .totalAmount(totalAmount)
                .build();

    }

    public String removeCartItem(Long cartItemId) {

        CartItem cartItem = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart Item not found"));

        cartItemRepo.delete(cartItem);

        return "Cart Item deleted successfully";
    }

    public String clearCart(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = cartRepo.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        cart.getCartItems().clear();
        cartRepo.save(cart);

        return "Cart is cleared successfully";
    }
}
