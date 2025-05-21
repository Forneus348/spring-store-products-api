package com.example.spring_product_api.repository;

import java.time.LocalDate;

public class ProductDto {
    private String name;
    private Double price;
    private String description;
    private LocalDate orderDateTime;

    public ProductDto(String name, Double price, String description, LocalDate orderDateTime) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.orderDateTime = orderDateTime;
    }

    public ProductDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDate orderDateTime) {
        this.orderDateTime = orderDateTime;
    }
}