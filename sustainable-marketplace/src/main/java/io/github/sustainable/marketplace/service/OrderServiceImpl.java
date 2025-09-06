package io.github.sustainable.marketplace.service;

import io.github.sustainable.marketplace.dto.response.OrderDto;
import io.github.sustainable.marketplace.entity.*;
import io.github.sustainable.marketplace.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, CartRepository cartRepository, CartItemRepository cartItemRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderDto createOrder(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Cart cart = cartRepository.findByUserUserId(userId).orElseThrow();

        Order order = new Order();
        order.setUser(user);
        order.setStatus("pending");

        BigDecimal total = BigDecimal.ZERO;

        List<CartItem> cartItems = cartItemRepository.findByCartCartId(cart.getCartId());
        for (CartItem item : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPriceAtPurchase(item.getPriceAtAdd());

            total = total.add(item.getPriceAtAdd().multiply(BigDecimal.valueOf(item.getQuantity())));
            orderItemRepository.save(orderItem);
        }

        order.setTotalAmount(total);
        order = orderRepository.save(order);

        return mapToDto(order);
    }

    @Override
    public OrderDto getOrderById(UUID orderId) {
        return orderRepository.findById(orderId).map(this::mapToDto).orElseThrow();
    }

    @Override
    public List<OrderDto> getOrdersByUser(UUID userId) {
        return orderRepository.findByUserUserId(userId)
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public void cancelOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus("cancelled");
        orderRepository.save(order);
    }

    private OrderDto mapToDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setOrderId(order.getOrderId());
        dto.setUserId(order.getUser().getUserId());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        return dto;
    }
}