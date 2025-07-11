package com.example.user_service.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private Double price;
    private String description;
    private LocalDate orderDateTime;

    public static ProductResponse fromMap(Map<String, Object> map) {
        if (map == null || !map.containsKey("data")) {
            throw new IllegalArgumentException("Invalid product data");
        }

        Map<String, Object> data = (Map<String, Object>) map.get("data");
        ProductResponse response = new ProductResponse();
        response.setId(Long.valueOf(data.get("id").toString()));
        response.setName((String) data.get("name"));
        response.setPrice(Double.valueOf(data.get("price").toString()));
        response.setDescription((String) data.get("description"));

        if (data.get("orderDateTime") != null) {
            response.setOrderDateTime(LocalDate.parse(data.get("orderDateTime").toString()));
        }

        return response;
    }
}
