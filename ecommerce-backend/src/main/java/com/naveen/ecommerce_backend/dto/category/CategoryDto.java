package com.naveen.ecommerce_backend.dto.category;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto implements Serializable {

    private Long id;
    private String name;
}
