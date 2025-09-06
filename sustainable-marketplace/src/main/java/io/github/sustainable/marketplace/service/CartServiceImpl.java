package io.github.sustainable.marketplace.service;

import io.github.sustainable.marketplace.dto.response.CartDto;
import io.github.sustainable.marketplace.dto.response.CartItemDto;
import io.github.sustainable.marketplace.entity.Cart;
import io.github.sustainable.marketplace.entity.CartItem;
import io.github.sustainable.marketplace.entity.Product;
import io.github.sustainable.marketplace.entity.User;
import io.github.sustainable.marketplace.repository.CartItemRepository;
import io.github.sustainable.marketplace.repository.CartRepository;
import io.github.sustainable.marketplace.repository.ProductRepository;
import io.github.sustainable.marketplace.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
//@AllArgsConstructor
@Data
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartDto getCartByUser(UUID userId) {
          Cart cart = cartRepository.findByUserUserId(userId).orElseThrow();
        return mapToDto(cart);
    }

    @Override
    public CartDto createCartForUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Cart cart = new Cart();
        cart.setUser(user);
        return mapToDto(cartRepository.save(cart));
    }

    @Override
    public CartItemDto addItemToCart(UUID userId, UUID productId, int quantity) {
        Cart cart = cartRepository.findByUserUserId(userId).orElseThrow();
        Product product = productRepository.findById(productId).orElseThrow();

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setPriceAtAdd(product.getPrice());

        return mapToDto(cartItemRepository.save(cartItem));
    }

    @Override
    public CartItemDto updateCartItem(UUID userId, UUID productId, int quantity) {
        Cart cart = cartRepository.findByUserUserId(userId).orElseThrow();
        CartItem cartItem = cartItemRepository.findByCartCartIdAndProductProductId(cart.getCartId(), productId)
                .orElseThrow();

        cartItem.setQuantity(quantity);
        return mapToDto(cartItemRepository.save(cartItem));
    }

    @Override
    public void removeCartItem(UUID userId, UUID productId) {
        Cart cart = cartRepository.findByUserUserId(userId).orElseThrow();
        cartItemRepository.deleteByCartCartIdAndProductProductId(cart.getCartId(), productId);
    }

    @Override
    public List<CartItemDto> getCartItems(UUID userId) {
        Cart cart = cartRepository.findByUserUserId(userId).orElseThrow();
        return cartItemRepository.findByCartCartId(cart.getCartId())
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private CartDto mapToDto(Cart cart) {
        CartDto dto = new CartDto();
        dto.setCartId(cart.getCartId());
        dto.setUserId(cart.getUser().getUserId());
        dto.setCreatedAt(cart.getCreatedAt());
        dto.setUpdatedAt(cart.getUpdatedAt());
        return dto;
    }

    private CartItemDto mapToDto(CartItem cartItem) {
        CartItemDto dto = new CartItemDto();
        dto.setCartItemId(cartItem.getCartItemId());
        dto.setProductId(cartItem.getProduct().getProductId());
        dto.setQuantity(cartItem.getQuantity());
        dto.setPriceAtAdd(cartItem.getPriceAtAdd());
        dto.setCreatedAt(cartItem.getCreatedAt());
        return dto;
    }
}