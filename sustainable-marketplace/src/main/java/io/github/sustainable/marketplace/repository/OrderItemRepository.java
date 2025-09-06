package io.github.sustainable.marketplace.repository;

import io.github.sustainable.marketplace.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
    List<OrderItem> findByOrderOrderId(UUID orderId);
    Optional<OrderItem> findByOrderOrderIdAndProductProductId(UUID orderId, UUID productId);
}