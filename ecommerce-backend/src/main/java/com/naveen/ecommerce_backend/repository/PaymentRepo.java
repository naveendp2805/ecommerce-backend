package com.naveen.ecommerce_backend.repository;

import com.naveen.ecommerce_backend.model.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
}
