package com.naveen.ecommerce_backend.controller;

import com.naveen.ecommerce_backend.dto.payment.PaymentRequest;
import com.naveen.ecommerce_backend.dto.payment.PaymentResponse;
import com.naveen.ecommerce_backend.dto.payment.VerifyPaymentRequest;
import com.naveen.ecommerce_backend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create-order")
    public ResponseEntity<PaymentResponse> createPaymentOrder(@RequestBody PaymentRequest request) throws Exception{
        return ResponseEntity.ok(paymentService.createPaymentOrder(request));
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyPaymentOrder(@RequestBody VerifyPaymentRequest request) throws Exception {
        return ResponseEntity.ok(paymentService.verifyPaymentOrder(request));
    }
}
