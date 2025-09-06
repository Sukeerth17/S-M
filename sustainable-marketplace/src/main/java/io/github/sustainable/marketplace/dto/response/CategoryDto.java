package io.github.sustainable.marketplace.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class CategoryDto {
    private UUID categoryId;
    private String categoryName;

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}