package com.example.spring_product_api.repository;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProductNotFoundException extends ResponseStatusException {
    public ProductNotFoundException(HttpStatus status, String message) {
        super(status, message);
    }
}