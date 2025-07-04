package com.example.user_service.dto;

import lombok.Data;

@Data
public class OrderItemResponse {
    private Long productId;
    private String productName;
    private Double price;
    private Integer quantity;
}