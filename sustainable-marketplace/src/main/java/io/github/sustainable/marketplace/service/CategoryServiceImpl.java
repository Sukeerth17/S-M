package io.github.sustainable.marketplace.service;

import io.github.sustainable.marketplace.dto.response.CategoryDto;
import io.github.sustainable.marketplace.entity.Category;
import io.github.sustainable.marketplace.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto createCategory(String categoryName) {
        Category category = new Category();
        category.setCategoryName(categoryName);
        return mapToDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto getCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId).map(this::mapToDto).orElseThrow();
    }

    @Override
    public CategoryDto getCategoryByName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName).map(this::mapToDto).orElseThrow();
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private CategoryDto mapToDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        return dto;
    }
}