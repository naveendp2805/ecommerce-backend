package com.naveen.ecommerce_backend.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogoutRequest {

    private String refreshToken;
}
