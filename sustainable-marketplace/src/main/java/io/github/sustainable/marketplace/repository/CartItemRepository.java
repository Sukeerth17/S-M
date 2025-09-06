package io.github.sustainable.marketplace.repository;


import io.github.sustainable.marketplace.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    List<CartItem> findByCartCartId(UUID cartId);
    Optional<CartItem> findByCartCartIdAndProductProductId(UUID cartId, UUID productId);
    void deleteByCartCartIdAndProductProductId(UUID cartId, UUID productId);
}