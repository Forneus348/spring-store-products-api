package com.example.spring_product_api.repository;

import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private String description;
    @Column(name = "order_date_time")
    private LocalDate orderDateTime;

    public Product(Long id, String name, Double price, String description, LocalDate orderDateTime) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.orderDateTime = orderDateTime;
    }

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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