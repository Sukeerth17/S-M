package io.github.sustainable.marketplace.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "categories", schema = "sustainable_marketplace") // change schema if needed
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id", columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID categoryId;

    @Column(name = "category_name", nullable = false, unique = true, length = 100)
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