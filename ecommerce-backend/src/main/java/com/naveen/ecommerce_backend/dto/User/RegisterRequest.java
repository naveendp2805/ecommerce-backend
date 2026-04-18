package com.naveen.ecommerce_backend.dto.User;

import com.naveen.ecommerce_backend.model.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    private String name;
    private String email;
    private String password;
}
