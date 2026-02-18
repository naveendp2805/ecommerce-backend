package com.naveen.ecommerce_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name is required.")
    @Column(nullable = false)
    private String name;

    @Size(max = 1000,  message = "Description cannot exceed 1000 characters.")
    @Column(length = 1000)
    private String description;

    @NotNull(message = "Price is required.")
    @Positive(message = "Price must be greater than 0.")
    @Column(nullable = false)
    private BigDecimal price;

    @NotNull(message = "Stock Quantity is required.")
    @Min(value = 0, message = "Stock Quantity cannot be negative.")
    @Column(nullable = false)
    private Integer stockQuantity;

    @Size(max = 500, message = "Image URL is too long.")
    private String imageUrl;

}
