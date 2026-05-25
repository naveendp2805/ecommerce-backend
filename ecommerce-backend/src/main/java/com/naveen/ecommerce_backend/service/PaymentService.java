package com.naveen.ecommerce_backend.service;

import com.naveen.ecommerce_backend.dto.payment.PaymentRequest;
import com.naveen.ecommerce_backend.dto.payment.PaymentResponse;
import com.naveen.ecommerce_backend.exception.ResourceNotFoundException;
import com.naveen.ecommerce_backend.model.order.Order;
import com.naveen.ecommerce_backend.model.payment.Payment;
import com.naveen.ecommerce_backend.model.payment.PaymentStatus;
import com.naveen.ecommerce_backend.repository.OrderRepo;
import com.naveen.ecommerce_backend.repository.PaymentRepo;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepo paymentRepo;
    private final OrderRepo orderRepo;

    private final RazorpayClient razorpayClient;

    @Value("${razorpay.key}")
    private String razorpayKey;

    public PaymentResponse createPaymentOrder(PaymentRequest request) throws Exception {

        Order order = orderRepo.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found!!"));

        int amountInPaise = order.getTotalAmount()
                .multiply(BigDecimal.valueOf(100))
                .intValue();

        JSONObject options = new JSONObject();

        options.put("amount", amountInPaise);
        options.put("currency", "INR");
        options.put("receipt", "order_" + order.getId());

        com.razorpay.Order razorpayOrder = razorpayClient.orders.create(options);

        Payment payment = Payment.builder()
                .razorpayOrderId(razorpayOrder.get("id"))
                .amount(order.getTotalAmount())
                .paymentStatus(PaymentStatus.PENDING)
                .order(order)
                .build();

        paymentRepo.save(payment);

        return new PaymentResponse(razorpayOrder.get("id"), amountInPaise, "INR", razorpayKey);
    }
}
