package com.naveen.ecommerce_backend.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductRequest {

    @NotBlank(message = "Product name is required")
    private String name;

    @Size(max = 1000,  message = "Description cannot exceed 1000 characters.")
    private String description;

    @NotNull(message = "Price is required.")
    @Positive(message = "Price must be greater than 0.")
    private BigDecimal price;

    @NotNull(message = "Stock Quantity is required.")
    @Min(value = 0, message = "Stock Quantity cannot be negative.")
    private Integer stockQuantity;

}
