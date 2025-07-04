package com.example.product_service.controller;

import com.example.product_service.repository.Product;
import com.example.product_service.repository.ProductDto;
import com.example.product_service.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/products")
public class ProductController {
@Autowired
private final ProductService productService;

public ProductController(ProductService productService) {
    this.productService = productService;
}

@PostMapping("/validate")
public ResponseEntity<Map<String, Object>> validateProducts(@RequestBody List<Long> productIds) {
    boolean allExist = productService.validateProductIds(productIds);

    if (!allExist) {
        Map<String, Object> response = createResponse(
                HttpStatus.NOT_FOUND.value(),
                List.of("Some products not found"),
                false
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    Map<String, Object> response = createResponse(
            HttpStatus.OK.value(),
            List.of(),
            true
    );
    return ResponseEntity.ok(response);
}

@GetMapping(path = "/search/{name}")
public ResponseEntity<Map<String, Object>> findByRegistr(@PathVariable String name) {
    List<Product> products = productService.findByNameSubstring(name);
    Map<String, Object> response = createResponse(
            HttpStatus.OK.value(),
            products.isEmpty() ? List.of("No products found") : List.of(),
            products
    );
    return ResponseEntity.ok(response);
}

@GetMapping
public ResponseEntity<Map<String, Object>> findAll() {
    List<Product> products = productService.findAll();
    return ResponseEntity.ok(createResponse(HttpStatus.OK.value(), List.of(), products));
}

@GetMapping(path = "{id}")
public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {
    Product product = productService.findById(id);
    if (product == null) {
        Map<String, Object> response = createResponse(
                HttpStatus.NOT_FOUND.value(),
                List.of("Product not found with id: " + id),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    return ResponseEntity.ok(createResponse(HttpStatus.OK.value(), List.of(), product));
}

@PostMapping
public ResponseEntity<Map<String, Object>> create(@RequestBody ProductDto productDto) {
    Product product = productService.create(productDto);
    if (product == null) {
        Map<String, Object> response = createResponse(
                HttpStatus.BAD_REQUEST.value(),
                List.of("Invalid product data"),
                null
        );
        return ResponseEntity.badRequest().body(response);
    }
    Map<String, Object> response = createResponse(
            HttpStatus.CREATED.value(),
            List.of(),
            product
    );
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}

@DeleteMapping(path = "{id}")
public ResponseEntity<Map<String, Object>> delete(@PathVariable(name = "id") Long id) {
    Product product = productService.findById(id);
    if (product == null) {
        Map<String, Object> response = createResponse(
                HttpStatus.NOT_FOUND.value(),
                List.of("Product not found with id: " + id),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    Product deletedProduct = productService.delete(id);
    return ResponseEntity.ok(createResponse(HttpStatus.OK.value(), List.of(), deletedProduct));
}

@PutMapping(path = "{id}")
public ResponseEntity<Map<String, Object>> update(
        @PathVariable Long id,
        @Valid @RequestBody ProductDto productDto
) {
    Product existingProduct = productService.findById(id);
    if (existingProduct == null) {
        Map<String, Object> response = createResponse(
                HttpStatus.NOT_FOUND.value(),
                List.of("Product not found with id: " + id),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    Product updatedProduct = productService.update(id, productDto);
    if (updatedProduct == null) {
        Map<String, Object> response = createResponse(
                HttpStatus.BAD_REQUEST.value(),
                List.of("Failed to update product"),
                null
        );
        return ResponseEntity.badRequest().body(response);
    }

    return ResponseEntity.ok(createResponse(HttpStatus.OK.value(), List.of(), updatedProduct));
}

private Map<String, Object> createResponse(int status, List<String> errors, Object data) {
    Map<String, Object> response = new LinkedHashMap<>();
    response.put("status", status);
    response.put("errors", errors);
    response.put("data", data);
    return response;
}
}
