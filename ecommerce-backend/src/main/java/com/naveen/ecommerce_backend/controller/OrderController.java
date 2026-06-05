package com.naveen.ecommerce_backend.controller;

import com.naveen.ecommerce_backend.dto.Order.OrderResponse;
import com.naveen.ecommerce_backend.model.order.OrderStatus;
import com.naveen.ecommerce_backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<OrderResponse>> getMyOrders(Authentication authentication,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "5") int size,
                                                           @RequestParam(defaultValue = "orderDate") String sortBy) {

        return ResponseEntity.ok(orderService.getMyOrders(authentication, page, size, sortBy));
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Long orderId,
                                                           @RequestParam OrderStatus orderStatus) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, orderStatus));
    }
}
