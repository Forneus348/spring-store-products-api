package com.example.user_service.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private Long userId;
    private List<OrderItemResponse> items;
    private Double totalPrice;
    private LocalDateTime createdAt;
    private String status;
}