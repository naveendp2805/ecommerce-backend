package com.naveen.ecommerce_backend.dto.User;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {

    private String email;
    private String password;
}
