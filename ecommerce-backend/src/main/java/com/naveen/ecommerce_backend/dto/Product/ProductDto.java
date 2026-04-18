package com.naveen.ecommerce_backend.dto.Product;

import com.naveen.ecommerce_backend.dto.Category.CategoryDto;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String imageUrl;

    private CategoryDto category;
}
