package io.github.sustainable.marketplace.controller;

import io.github.sustainable.marketplace.dto.response.CartDto;
import io.github.sustainable.marketplace.dto.response.CartItemDto;
import io.github.sustainable.marketplace.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cart")
//@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID userId) {
        return ResponseEntity.ok(cartService.getCartByUser(userId));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> createCart(@PathVariable UUID userId) {
        return ResponseEntity.ok(cartService.createCartForUser(userId));
    }

    @PostMapping("/{userId}/items/{productId}")
    public ResponseEntity<CartItemDto> addItem(
            @PathVariable UUID userId,
            @PathVariable UUID productId,
            @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.addItemToCart(userId, productId, quantity));
    }

    @PutMapping("/{userId}/items/{productId}")
    public ResponseEntity<CartItemDto> updateItem(
            @PathVariable UUID userId,
            @PathVariable UUID productId,
            @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.updateCartItem(userId, productId, quantity));
    }

    @DeleteMapping("/{userId}/items/{productId}")
    public ResponseEntity<Void> removeItem(@PathVariable UUID userId, @PathVariable UUID productId) {
        cartService.removeCartItem(userId, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/items")
    public ResponseEntity<List<CartItemDto>> getCartItems(@PathVariable UUID userId) {
        return ResponseEntity.ok(cartService.getCartItems(userId));
    }
}