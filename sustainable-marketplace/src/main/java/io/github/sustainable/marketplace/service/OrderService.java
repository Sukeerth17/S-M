package io.github.sustainable.marketplace.service;

import io.github.sustainable.marketplace.dto.response.OrderDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderDto createOrder(UUID userId);
    OrderDto getOrderById(UUID orderId);
    List<OrderDto> getOrdersByUser(UUID userId);
    void cancelOrder(UUID orderId);
}