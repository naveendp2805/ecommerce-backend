package com.naveen.ecommerce_backend.dto.admin;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardStatsResponse {

    private Long products;
    private Long categories;
    private Long orders;
    private Long users;
}