package com.naveen.ecommerce_backend.dto.user;

import com.naveen.ecommerce_backend.model.user.Role;
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
    private Role role;

}
