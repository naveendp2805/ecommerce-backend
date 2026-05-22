package com.naveen.ecommerce_backend.model.Order;

import com.naveen.ecommerce_backend.model.User.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Total amount cannot be null.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be greater than 0.")
    private BigDecimal totalAmount;

    @NotNull(message = "Order date cannot be null.")
    private LocalDateTime orderDate;

    @NotNull(message = "Order status cannot be null.")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @NotNull(message = "User cannot be null")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull(message = "Order items cannot be null")
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;
}
