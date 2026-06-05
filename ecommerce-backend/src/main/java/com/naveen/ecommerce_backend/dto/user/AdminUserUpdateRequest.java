package com.naveen.ecommerce_backend.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminUserUpdateRequest {

    private String name;
    private String email;
    private String role;
}