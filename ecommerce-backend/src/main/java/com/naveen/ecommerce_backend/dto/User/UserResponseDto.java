package com.naveen.ecommerce_backend.dto.User;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private Long id;
    private String name;
    private String email;
}
