package com.naveen.ecommerce_backend.dto.cart;

import lombok.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemResponse {

    private Long cartItemId;
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subTotal;
}
