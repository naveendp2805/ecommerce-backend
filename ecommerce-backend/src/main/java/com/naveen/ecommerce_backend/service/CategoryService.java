package com.naveen.ecommerce_backend.service;

import com.naveen.ecommerce_backend.dto.CategoryDto;
import com.naveen.ecommerce_backend.dto.CategoryMapper;
import com.naveen.ecommerce_backend.model.Category;
import com.naveen.ecommerce_backend.repository.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepo categoryRepo;

    public CategoryDto createCategory(CategoryDto dto) {

        if(categoryRepo.findByName(dto.getName()).isPresent())
            throw new RuntimeException("Category already exists!!");

        Category category = CategoryMapper.toEntity(dto);

        Category saved = categoryRepo.save(category);

        return CategoryMapper.toDto(saved);
    }

    public CategoryDto updateCategoryById(Long id, CategoryDto dto)
    {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not Found!!"));

        category.setName(dto.getName());

        return CategoryMapper.toDto(categoryRepo.save(category));
    }

    public List<CategoryDto> getAllCategories() {

        return categoryRepo.findAll()
                .stream()
                .map(CategoryMapper::toDto)
                .toList();
    }

    public CategoryDto getCategoryById(Long id) {

        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not Found!!"));

        return CategoryMapper.toDto(category);
    }

    public void deleteCategoryById(Long id) {

        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not Found!!"));

        if(!category.getProducts().isEmpty())
            throw new RuntimeException("Cannot delete category with products.");

        categoryRepo.delete(category);
    }


}
