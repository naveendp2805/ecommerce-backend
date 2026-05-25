package com.naveen.ecommerce_backend.service;

import com.naveen.ecommerce_backend.dto.payment.PaymentRequest;
import com.naveen.ecommerce_backend.dto.payment.PaymentResponse;
import com.naveen.ecommerce_backend.dto.payment.VerifyPaymentRequest;
import com.naveen.ecommerce_backend.exception.ResourceNotFoundException;
import com.naveen.ecommerce_backend.model.order.Order;
import com.naveen.ecommerce_backend.model.order.OrderStatus;
import com.naveen.ecommerce_backend.model.payment.Payment;
import com.naveen.ecommerce_backend.model.payment.PaymentStatus;
import com.naveen.ecommerce_backend.repository.OrderRepo;
import com.naveen.ecommerce_backend.repository.PaymentRepo;
import com.razorpay.RazorpayClient;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepo paymentRepo;
    private final OrderRepo orderRepo;

    private final RazorpayClient razorpayClient;

    @Value("${razorpay.key}")
    private String razorpayKey;

    @Value("${razorpay.secret}")
    private String razorpaySecret;

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

    public String verifyPaymentOrder(VerifyPaymentRequest request) throws Exception{

        String data = request.getRazorpayOrderId() + "|" + request.getRazorpayPaymentId();

        String generatedSignature = generateSignature(data);

        if(!generatedSignature.equals(request.getRazorpaySignature())) throw new RuntimeException("Invalid payment signature!!");

        Payment payment = paymentRepo.findByRazorpayOrderId(request.getRazorpayOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found!!"));

        payment.setRazorpayPaymentId(request.getRazorpayPaymentId());
        payment.setRazorpaySignature(generatedSignature);
        payment.setPaymentStatus(PaymentStatus.SUCCESS);

        paymentRepo.save(payment);

        Order order = payment.getOrder();
        order.setOrderStatus(OrderStatus.CONFIRMED);
        orderRepo.save(order);

        return "Payment verified successfully!";
    }

    public String generateSignature(String data) throws Exception{

        Mac sha256Hmac = Mac.getInstance("HmacSHA256");

        SecretKeySpec secretKey = new SecretKeySpec(razorpaySecret.getBytes(), "HmacSHA256");

        sha256Hmac.init(secretKey);

        byte[] hash = sha256Hmac.doFinal(data.getBytes());

        return new String(org.apache.commons.codec.binary.Hex.encodeHex(hash));
    }
}
