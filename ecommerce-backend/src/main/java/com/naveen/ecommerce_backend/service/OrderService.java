package com.naveen.ecommerce_backend.service;

import com.naveen.ecommerce_backend.dto.Order.OrderItemResponse;
import com.naveen.ecommerce_backend.dto.Order.OrderResponse;
import com.naveen.ecommerce_backend.exception.ResourceNotFoundException;
import com.naveen.ecommerce_backend.exception.BadRequestException;
import com.naveen.ecommerce_backend.model.Cart.Cart;
import com.naveen.ecommerce_backend.model.Cart.CartItem;
import com.naveen.ecommerce_backend.model.Order.Order;
import com.naveen.ecommerce_backend.model.Order.OrderItem;
import com.naveen.ecommerce_backend.model.Order.OrderStatus;
import com.naveen.ecommerce_backend.model.Product.Product;
import com.naveen.ecommerce_backend.model.User.User;
import com.naveen.ecommerce_backend.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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
                .orderDate(savedOrder.getOrderDate())
                .orderStatus(savedOrder.getOrderStatus())
                .items(orderItemResponses)
                .totalAmount(totalAmount)
                .build();
    }

    public List<OrderResponse> getMyOrders(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!!"));

        List<Order> orders = orderRepo.findByUser(user);

        List<OrderResponse> orderResponses = new ArrayList<>();

        for(Order order : orders)
        {
            List<OrderItemResponse> orderItemResponses = new ArrayList<>();
            for(OrderItem orderItem : order.getOrderItems())
            {
                OrderItemResponse orderItemResponse = OrderItemResponse.builder()
                        .productId(orderItem.getProduct().getId())
                        .productName(orderItem.getProduct().getName())
                        .price(orderItem.getPriceAtPurchase())
                        .quantity(orderItem.getQuantity())
                        .subTotal(orderItem.getSubTotal())
                        .build();

                orderItemResponses.add(orderItemResponse);
            }

            OrderResponse orderResponse = OrderResponse.builder()
                    .orderId(order.getId())
                    .orderDate(order.getOrderDate())
                    .orderStatus(order.getOrderStatus())
                    .totalAmount(order.getTotalAmount())
                    .items(orderItemResponses)
                    .build();

            orderResponses.add(orderResponse);
        }

        return orderResponses;
    }

}
