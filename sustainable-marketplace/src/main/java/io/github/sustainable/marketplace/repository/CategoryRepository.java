package io.github.sustainable.marketplace.repository;

import io.github.sustainable.marketplace.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findByCategoryName(String categoryName);
}