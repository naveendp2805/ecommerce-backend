package com.naveen.ecommerce_backend.service;

import com.naveen.ecommerce_backend.exception.ResourceNotFoundException;
import com.naveen.ecommerce_backend.model.Product;
import com.naveen.ecommerce_backend.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;

    public Product createProduct(Product product) {
        return productRepo.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product getProductById(Long id) {
        return productRepo.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

    public Product updateProductById(Long id, Product product) {

        Product existingProduct = getProductById(id);

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStockQuantity(product.getStockQuantity());
        existingProduct.setImageUrl(product.getImageUrl());

        return productRepo.save(existingProduct);
    }

    public void deleteProductById(Long id) {
        Product existingProduct = getProductById(id);
        productRepo.delete(existingProduct);
    }

}
