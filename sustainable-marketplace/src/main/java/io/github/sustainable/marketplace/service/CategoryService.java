package io.github.sustainable.marketplace.service;

import io.github.sustainable.marketplace.dto.response.CategoryDto;
import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryDto createCategory(String categoryName);
    CategoryDto getCategoryById(UUID categoryId);
    CategoryDto getCategoryByName(String categoryName);
    List<CategoryDto> getAllCategories();
}