package com.naveen.ecommerce_backend.dto.payment;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {

    private Long orderId;
}
