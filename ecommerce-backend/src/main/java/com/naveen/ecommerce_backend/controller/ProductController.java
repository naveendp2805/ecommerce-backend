package com.naveen.ecommerce_backend.controller;

import com.naveen.ecommerce_backend.model.Product;
import com.naveen.ecommerce_backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping
    public Product createProduct(@RequestParam String name,
                                 @RequestParam String description,
                                 @RequestParam BigDecimal price,
                                 @RequestParam Integer stockQuantity,
                                 @RequestParam("image") MultipartFile image) throws IOException {

        return productService.createProduct(name, description, price, stockQuantity, image);
    }

    @GetMapping
    public List<Product> findAll() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public Product updateProductById(@PathVariable Long id,
                                     @RequestParam String name,
                                     @RequestParam String description,
                                     @RequestParam BigDecimal price,
                                     @RequestParam Integer stockQuantity,
                                     @RequestParam(required = false) MultipartFile image) throws IOException {
        return productService.updateProductById(id, name, description, price, stockQuantity, image);
    }

    @DeleteMapping("/{id}")
    public String deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return "Product with id: " + id + " has been deleted";
    }

    @GetMapping("/paged")
    public Page<Product> getProductsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        return productService.getProductsByPage(page, size, sortBy);
    }
}
