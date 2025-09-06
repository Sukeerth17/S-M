package io.github.sustainable.marketplace.service;

import io.github.sustainable.marketplace.dto.response.CartDto;
import io.github.sustainable.marketplace.dto.response.CartItemDto;

import java.util.List;
import java.util.UUID;

public interface CartService {
    CartDto getCartByUser(UUID userId);
    CartDto createCartForUser(UUID userId);
    CartItemDto addItemToCart(UUID userId, UUID productId, int quantity);
    CartItemDto updateCartItem(UUID userId, UUID productId, int quantity);
    void removeCartItem(UUID userId, UUID productId);
    List<CartItemDto> getCartItems(UUID userId);
}