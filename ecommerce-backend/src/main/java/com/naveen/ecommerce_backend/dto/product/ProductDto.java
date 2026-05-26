package com.naveen.ecommerce_backend.dto.product;

import com.naveen.ecommerce_backend.dto.category.CategoryDto;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto implements Serializable {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String imageUrl;

    private CategoryDto category;
}
