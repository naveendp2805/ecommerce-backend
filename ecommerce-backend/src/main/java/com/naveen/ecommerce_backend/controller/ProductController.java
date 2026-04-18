package com.naveen.ecommerce_backend.controller;

import com.naveen.ecommerce_backend.dto.Product.CreateProductRequest;
import com.naveen.ecommerce_backend.dto.Product.ProductDto;
import com.naveen.ecommerce_backend.dto.Product.UpdateProductRequest;
import com.naveen.ecommerce_backend.model.Product;
import com.naveen.ecommerce_backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDto> createProduct(@ModelAttribute @Valid CreateProductRequest productRequest) throws IOException {

        ProductDto savedProduct = productService.createProduct(productRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProductsByCategoryId(@RequestParam(required = false) Long categoryId)
    {
        if(categoryId != null)
            return ResponseEntity.ok(productService.getProductsByCategoryId(categoryId));

        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProductById(@PathVariable Long id,
                                                        @ModelAttribute @Valid UpdateProductRequest productRequest,
                                                        @RequestParam(required = false) MultipartFile image) throws IOException {

        ProductDto updatedProduct = productService.updateProductById(id, productRequest, image);

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public String deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return "Product with id: " + id + " is deleted";
    }

    @GetMapping("/paged")
    public Page<Product> getProductsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        return productService.getProductsByPage(page, size, sortBy);
    }
}
