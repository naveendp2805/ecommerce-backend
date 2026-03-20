package com.naveen.ecommerce_backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Fetch;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
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

    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss a")
    private LocalDateTime createdAt;

    @NotNull(message = "Price is required.")
    @Positive(message = "Price must be greater than 0.")
    @Column(nullable = false)
    private BigDecimal price;

    @NotNull(message = "Stock Quantity is required.")
    @Min(value = 0, message = "Stock Quantity cannot be negative.")
    @Column(nullable = false)
    private Integer stockQuantity;

    @Size(max = 500, message = "Image URL is too long.")
    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @PrePersist
    public void beforeInsert(){
        this.createdAt = LocalDateTime.now();
    }

}
