package io.github.sustainable.marketplace.repository;

import io.github.sustainable.marketplace.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByUserUserId(UUID userId);
}