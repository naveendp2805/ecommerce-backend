package com.naveen.ecommerce_backend.service;

import com.naveen.ecommerce_backend.dto.CreateProductRequest;
import com.naveen.ecommerce_backend.dto.ProductDto;
import com.naveen.ecommerce_backend.dto.ProductMapper;
import com.naveen.ecommerce_backend.dto.UpdateProductRequest;
import com.naveen.ecommerce_backend.exception.ResourceNotFoundException;
import com.naveen.ecommerce_backend.model.Product;
import com.naveen.ecommerce_backend.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;

    private static final String IMAGE_URL_PREFIX = "/uploads/";

    @Value("${file.upload-dir}")
    private String uploadDir;

    public ProductDto createProduct(CreateProductRequest productRequest) throws IOException
    {
        MultipartFile image = productRequest.getImage();

        String imageFileName = image.getOriginalFilename();
        if(imageFileName == null){
            throw new RuntimeException("Image is required");
        }

        String fileName = System.currentTimeMillis() + "_" + imageFileName;

        Path uploadPath = Paths.get(System.getProperty("user.dir"), uploadDir);
        Files.createDirectories(uploadPath);

        Path filePath = uploadPath.resolve(fileName);

        image.transferTo(filePath);

        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .stockQuantity(productRequest.getStockQuantity())
                .imageUrl("/" + uploadDir + "/" + fileName)
                .build();

        Product saveProduct = productRepo.save(product);

        return ProductMapper.toDto(saveProduct);
    }

    public List<ProductDto> getAllProducts() {

        return productRepo.findAll()
                .stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(Long id) {
        Product product =  productRepo.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));

        return ProductMapper.toDto(product);
    }

    public ProductDto updateProductById(Long id,
                                     UpdateProductRequest productRequest,
                                     MultipartFile image) throws IOException
    {

        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setName(productRequest.getName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setStockQuantity(productRequest.getStockQuantity());

        if(image != null && !image.isEmpty())
        {
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            if(existingProduct.getImageUrl() != null)
            {
                String oldFileName = existingProduct.getImageUrl().replace(IMAGE_URL_PREFIX, "");
                Path oldFilePath = uploadPath.resolve(oldFileName);
                Files.deleteIfExists(oldFilePath);
            }

            String newFileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path newFilePath = uploadPath.resolve(newFileName);

            image.transferTo(newFilePath.toFile());

            existingProduct.setImageUrl("/uploads/" + newFileName);
        }

        Product updatedProduct = productRepo.save(existingProduct);

        return ProductMapper.toDto(updatedProduct);
    }

    public void deleteProductById(Long id) {

        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if(existingProduct.getImageUrl() != null)
        {
            String imagePath = System.getProperty("user.dir") + existingProduct.getImageUrl();

            Path filePath = Paths.get(imagePath);

            try{
                Files.deleteIfExists(filePath);
            } catch(IOException e) {
                throw new RuntimeException("Failed to delete image file", e);
            }
        }

        productRepo.delete(existingProduct);
    }

    public Page<Product> getProductsByPage(int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return productRepo.findAll(pageable);
    }
}
