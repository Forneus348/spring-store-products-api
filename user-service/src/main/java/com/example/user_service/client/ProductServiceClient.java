package com.example.user_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "product-service", url = "${product-service.url}")
public interface ProductServiceClient {
    @PostMapping("/api/products/validate")
    ResponseEntity<Map<String, Object>> validateProducts(@RequestBody List<Long> productIds);

    @GetMapping("/api/products/{id}")
    Map<String, Object> getProductById(@PathVariable Long id);
}
