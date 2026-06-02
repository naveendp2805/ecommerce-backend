package com.naveen.ecommerce_backend.dto.product;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CloudinaryResponse {

    private String imageUrl;
    private String publicId;
}
