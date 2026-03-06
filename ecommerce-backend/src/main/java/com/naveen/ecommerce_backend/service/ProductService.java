package com.naveen.ecommerce_backend.service;

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
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public Product createProduct(String name, String description, BigDecimal price, Integer stockQuantity, MultipartFile image) throws IOException {

        String imageFileName = image.getOriginalFilename();
        if(imageFileName == null){
            throw new RuntimeException("Image is required");
        }

        String fileName = System.currentTimeMillis() + "_" + imageFileName;

        Path uploadPath = Paths.get(System.getProperty("user.dir"), uploadDir);
        Files.createDirectories(uploadPath);

        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, image.getBytes());

        Product product = Product.builder()
                .name(name)
                .description(description)
                .price(price)
                .stockQuantity(stockQuantity)
                .imageUrl("/" + uploadDir + "/" + fileName)
                .build();

        return productRepo.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product getProductById(Long id) {
        return productRepo.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

    public Product updateProductById(Long id,
                                     String name,
                                     String description,
                                     BigDecimal price,
                                     Integer stockQuantity,
                                     MultipartFile image) throws IOException{

        Product existingProduct = getProductById(id);

        existingProduct.setName(name);
        existingProduct.setDescription(description);
        existingProduct.setPrice(price);
        existingProduct.setStockQuantity(stockQuantity);

        if(image != null && !image.isEmpty())
        {
            if(existingProduct.getImageUrl() != null)
            {
                String oldfileName = existingProduct.getImageUrl().replace("/uploads/", "");
                Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
                Path oldFilePath = uploadPath.resolve(oldfileName);
                Files.deleteIfExists(oldFilePath);
            }

            String newFileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();

            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            Path newFilePath = uploadPath.resolve(newFileName);
            Files.write(newFilePath, image.getBytes());

            existingProduct.setImageUrl("/uploads/" + newFileName);
        }

        return productRepo.save(existingProduct);
    }

    public void deleteProductById(Long id) {
        Product existingProduct = getProductById(id);

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
