package com.example.user_service.service;

import com.example.user_service.client.ProductServiceClient;
import com.example.user_service.dto.OrderItemRequest;
import com.example.user_service.dto.OrderRequest;
import com.example.user_service.dto.ProductResponse;
import com.example.user_service.entity.Order;
import com.example.user_service.entity.OrderItem;
import com.example.user_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


    @Service
    @RequiredArgsConstructor
    public class OrderService {
        private final OrderRepository orderRepository;
        private final ProductServiceClient productServiceClient;

        public Order createOrder(OrderRequest orderRequest) {
            // Проверка существования продуктов
            List<Long> productIds = orderRequest.getItems().stream()
                    .map(OrderItemRequest::getProductId)
                    .collect(Collectors.toList());

            ResponseEntity<Map<String, Object>> validationResponse =
                    productServiceClient.validateProducts(productIds);

            if (!validationResponse.getStatusCode().is2xxSuccessful() ||
                    !(Boolean) validationResponse.getBody().get("data")) {
                throw new IllegalArgumentException("One or more products are invalid");
            }

            // Создание заказа
            Order order = new Order();
            order.setUserId(orderRequest.getUserId());
            order.setCreatedAt(LocalDateTime.now());
            order.setStatus("CREATED");

            List<OrderItem> orderItems = new ArrayList<>();
            double totalPrice = 0.0;

            for (OrderItemRequest itemRequest : orderRequest.getItems()) {
                Map<String, Object> productMap = productServiceClient.getProductById(itemRequest.getProductId());
                ProductResponse product = ProductResponse.fromMap(productMap);

                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order);
                orderItem.setProductId(product.getId());
                orderItem.setProductName(product.getName());
                orderItem.setPrice(product.getPrice());
                orderItem.setQuantity(itemRequest.getQuantity());

                orderItems.add(orderItem);
                totalPrice += product.getPrice() * itemRequest.getQuantity();
            }

            order.setItems(orderItems);
            order.setTotalPrice(totalPrice);

            return orderRepository.save(order);
        }

        public Order getOrderById(Long id) {
            return orderRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Order not found"));
        }
}
