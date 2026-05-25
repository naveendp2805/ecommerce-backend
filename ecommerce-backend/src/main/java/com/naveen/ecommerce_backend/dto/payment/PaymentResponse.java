package com.naveen.ecommerce_backend.dto.payment;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {

    private String razorpayOrderId;

    private Integer amountInPaise;

    private String currency;

    private String key;
}
