package com.naveen.ecommerce_backend.dto.payment;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyPaymentRequest {

    private String razorpayOrderId;

    private String razorpayPaymentId;

    private String razorpaySignature;
}
