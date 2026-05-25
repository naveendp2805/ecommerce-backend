package com.naveen.ecommerce_backend.config;

import com.razorpay.RazorpayClient;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Setter
public class RazorpayConfig {

    @Value("${razorpay.key}")
    private String razorpayKey;

    @Value("${razorpay.secret}")
    private String razorpaySecret;


    @Bean
    public RazorpayClient razorpayClient() throws Exception {
        return new RazorpayClient(razorpayKey, razorpaySecret);
    }
}
