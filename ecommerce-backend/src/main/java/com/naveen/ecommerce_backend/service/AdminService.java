package com.naveen.ecommerce_backend.service;

import com.naveen.ecommerce_backend.dto.admin.DashboardStatsResponse;
import com.naveen.ecommerce_backend.repository.CategoryRepo;
import com.naveen.ecommerce_backend.repository.OrderRepo;
import com.naveen.ecommerce_backend.repository.ProductRepo;
import com.naveen.ecommerce_backend.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final ProductRepo productRepo;

    private final CategoryRepo categoryRepo;

    private final OrderRepo orderRepo;

    private final UserRepo userRepo;

    public DashboardStatsResponse getDashboardStats() {

        return DashboardStatsResponse.builder()
                .products(productRepo.count())
                .categories(categoryRepo.count())
                .orders(orderRepo.count())
                .users(userRepo.count())
                .build();
    }
}