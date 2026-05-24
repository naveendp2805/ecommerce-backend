package com.naveen.ecommerce_backend.repository;

import com.naveen.ecommerce_backend.model.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
}
