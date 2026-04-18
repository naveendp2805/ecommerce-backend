package com.naveen.ecommerce_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Email
    @NotBlank(message = "Email is required.")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password is required.")
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

}
