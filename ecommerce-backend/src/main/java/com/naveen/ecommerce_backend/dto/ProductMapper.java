package com.naveen.ecommerce_backend.dto;

import com.naveen.ecommerce_backend.model.Product;

public class ProductMapper {

    public static ProductDto toDto(Product product)
    {

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .imageUrl(product.getImageUrl())
                .category( product.getCategory() != null ? CategoryMapper.toDto(product.getCategory()) : null)
                .build();
    }

    public static Product toEntity(ProductDto dto)
    {
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stockQuantity(dto.getStockQuantity())
                .imageUrl(dto.getImageUrl())
                .build();
    }

}
