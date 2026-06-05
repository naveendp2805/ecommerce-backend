package com.naveen.ecommerce_backend.dto.Order;

import com.naveen.ecommerce_backend.model.order.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderResponse {

    private Long orderId;

    private String customerName;
    private String customerEmail;

    private BigDecimal totalAmount;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private List<OrderItemResponse> items;

}
