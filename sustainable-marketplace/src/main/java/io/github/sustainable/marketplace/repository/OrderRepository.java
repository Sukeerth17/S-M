package io.github.sustainable.marketplace.repository;


import io.github.sustainable.marketplace.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUserUserId(UUID userId);
}