package com.naveen.ecommerce_backend.repository;

import com.naveen.ecommerce_backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {

    List<Product> findProductByCategoryId(Long categoryId);
}
