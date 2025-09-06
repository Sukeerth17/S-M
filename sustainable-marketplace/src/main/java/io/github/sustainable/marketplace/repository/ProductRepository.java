package io.github.sustainable.marketplace.repository;


import io.github.sustainable.marketplace.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByUserUserId(UUID userId);
    List<Product> findByCategoryCategoryId(UUID categoryId);
}