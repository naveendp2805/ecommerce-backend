package com.naveen.ecommerce_backend.controller;

import com.naveen.ecommerce_backend.dto.Order.OrderResponse;
import com.naveen.ecommerce_backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<OrderResponse> placeOrder(Authentication authentication) {

        return ResponseEntity.ok(orderService.placeOrder(authentication));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getMyOrders(Authentication authentication) {

        return ResponseEntity.ok(orderService.getMyOrders(authentication));
    }
}
