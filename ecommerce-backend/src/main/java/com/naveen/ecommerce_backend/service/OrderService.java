package com.naveen.ecommerce_backend.service;

import com.naveen.ecommerce_backend.dto.Order.OrderItemResponse;
import com.naveen.ecommerce_backend.dto.Order.OrderResponse;
import com.naveen.ecommerce_backend.exception.ResourceNotFoundException;
import com.naveen.ecommerce_backend.exception.BadRequestException;
import com.naveen.ecommerce_backend.model.cart.Cart;
import com.naveen.ecommerce_backend.model.cart.CartItem;
import com.naveen.ecommerce_backend.model.order.Order;
import com.naveen.ecommerce_backend.model.order.OrderItem;
import com.naveen.ecommerce_backend.model.order.OrderStatus;
import com.naveen.ecommerce_backend.model.product.Product;
import com.naveen.ecommerce_backend.model.user.User;
import com.naveen.ecommerce_backend.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepo orderRepo;
    private final CartItemRepo cartItemRepo;
    private final CartRepo cartRepo;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;

    public OrderResponse placeOrder(Authentication authentication)  {

        String email = authentication.getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!!"));

        Cart cart = cartRepo.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!!"));

        List<CartItem> cartItems = cartItemRepo.findByCart(cart);
        if(cartItems.isEmpty()) throw new BadRequestException("Cart is Empty!!");

        Order order = Order.builder()
                .user(user)
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.PENDING)
                .build();

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        List<OrderItemResponse> orderItemResponses = new ArrayList<>();

        for(CartItem cartItem : cartItems)
        {
            Product product = cartItem.getProduct();
            Integer quantity = cartItem.getQuantity();

            if(product.getStockQuantity() < quantity) throw new BadRequestException(product.getName() + " is out of stock!!");

            BigDecimal subTotal = product.getPrice().multiply(BigDecimal.valueOf(quantity));

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(quantity)
                    .priceAtPurchase(product.getPrice())
                    .subTotal(subTotal)
                    .build();
            orderItems.add(orderItem);

            totalAmount = totalAmount.add(subTotal);

            product.setStockQuantity(product.getStockQuantity() - quantity);
            productRepo.save(product);

            OrderItemResponse orderItemResponse = OrderItemResponse.builder()
                    .productId(product.getId())
                    .productName(product.getName())
                    .quantity(quantity)
                    .price(product.getPrice())
                    .subTotal(subTotal)
                    .build();
            orderItemResponses.add(orderItemResponse);
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepo.save(order);

        cart.getCartItems().clear();
        cartRepo.save(cart);

        return OrderResponse.builder()
                .orderId(savedOrder.getId())
                .customerName(order.getUser().getName())
                .customerEmail(order.getUser().getEmail())
                .orderDate(savedOrder.getOrderDate())
                .orderStatus(savedOrder.getOrderStatus())
                .items(orderItemResponses)
                .totalAmount(totalAmount)
                .build();
    }

    public Page<OrderResponse> getMyOrders(Authentication authentication, int page, int size, String sortBy) {

        String email = authentication.getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!!"));

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());

        Page<Order> orders = orderRepo.findByUser(user, pageable);

        return orders.map(order -> {

            List<OrderItemResponse> orderItemResponses = order.getOrderItems()
                    .stream()
                    .map(orderItem ->
                            OrderItemResponse.builder()
                                    .productId(orderItem.getProduct().getId())
                                    .productName(orderItem.getProduct().getName())
                                    .quantity(orderItem.getQuantity())
                                    .price(orderItem.getPriceAtPurchase())
                                    .subTotal(orderItem.getSubTotal())
                                    .build())
                    .toList();

            return OrderResponse.builder()
                    .orderId(order.getId())
                    .customerName(order.getUser().getName())
                    .customerEmail(order.getUser().getEmail())
                    .orderDate(order.getOrderDate())
                    .orderStatus(order.getOrderStatus())
                    .items(orderItemResponses)
                    .totalAmount(order.getTotalAmount())
                    .build();
        });
    }

    public OrderResponse updateOrderStatus(Long orderId, OrderStatus orderStatus) {

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        order.setOrderStatus((orderStatus));

        orderRepo.save(order);

        List<OrderItemResponse> orderItemResponses =
                order.getOrderItems()
                        .stream()
                        .map(item -> OrderItemResponse.builder()
                                .productId(item.getProduct().getId())
                                .productName(item.getProduct().getName())
                                .quantity(item.getQuantity())
                                .price(item.getPriceAtPurchase())
                                .subTotal(item.getSubTotal())
                                .build())
                        .toList();

        return OrderResponse.builder()
                .orderId(order.getId())
                .customerName(order.getUser().getName())
                .customerEmail(order.getUser().getEmail())
                .orderDate(order.getOrderDate())
                .orderStatus(order.getOrderStatus())
                .items(orderItemResponses)
                .totalAmount(order.getTotalAmount())
                .build();
    }

}
