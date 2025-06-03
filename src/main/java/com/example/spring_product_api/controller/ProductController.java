package com.example.spring_product_api.controller;

import com.example.spring_product_api.repository.Product;
import com.example.spring_product_api.repository.ProductDto;
import com.example.spring_product_api.service.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    private Map<String, Object> createResponse(int status, List<String> errors, Object data) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", status);
        response.put("errors", errors);
        response.put("data", data);
        return response;
    }

    @GetMapping(path = "/search/{name}")
    public ResponseEntity<Map<String, Object>> findByNameSubstring(
            @PathVariable String name,
            HttpSession session
    ) {
        checkAuth(session);
        List<Product> products = productService.findByNameSubstring(name);
        Map<String, Object> response = createResponse(
                HttpStatus.OK.value(),
                products.isEmpty() ? List.of("No products found") : List.of(),
                products
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAll(HttpSession session) {
        checkAuth(session);
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(createResponse(HttpStatus.OK.value(), List.of(), products));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Map<String, Object>> findById(
            @PathVariable Long id,
            HttpSession session
    ) {
        checkAuth(session);
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
    public ResponseEntity<Map<String, Object>> create(
            @RequestBody ProductDto productDto,
            HttpSession session
    ) {
        checkAuth(session);
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
    public ResponseEntity<Map<String, Object>> delete(
            @PathVariable(name = "id") Long id,
            HttpSession session
    ) {
        checkAuth(session);
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
            @Valid @RequestBody ProductDto productDto,
            HttpSession session
    ) {
        checkAuth(session);
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

    private void checkAuth(HttpSession session) {
        if (session.getAttribute("user") == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authorized!");
        }
    }
}