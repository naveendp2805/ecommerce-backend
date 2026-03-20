package com.naveen.ecommerce_backend.controller;

import com.naveen.ecommerce_backend.dto.CategoryDto;
import com.naveen.ecommerce_backend.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto dto)
    {
        CategoryDto createdCategory = categoryService.createCategory(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategoryById(@PathVariable Long id,
                                                          @RequestBody CategoryDto dto)
    {
        return ResponseEntity.ok(categoryService.updateCategoryById(id, dto));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories()
    {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id)
    {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long id)
    {
        categoryService.deleteCategoryById(id);

        return ResponseEntity.ok("Product with id: " + id + " is deleted");
    }

}
