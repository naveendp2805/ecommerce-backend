package com.naveen.ecommerce_backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.naveen.ecommerce_backend.dto.product.CloudinaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryResponse uploadImage(MultipartFile file, String folder) throws IOException {

        Map<?, ?> uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap("folder", folder)
        );

        return new CloudinaryResponse(
                uploadResult.get("secure_url").toString(),
                uploadResult.get("public_id").toString()
        );
    }

    public void deleteImage(String publicId) throws IOException {

        cloudinary.uploader().destroy(
                publicId,
                ObjectUtils.emptyMap()
        );
    }
}
