package com.naveen.ecommerce_backend.dto.user;

import com.naveen.ecommerce_backend.model.user.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

    private String accessToken;
    private String refreshToken;

    private Long userId;
    private String email;
    private Role role;
}
