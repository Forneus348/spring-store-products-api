/*package com.example.spring_product_api.controller;

import com.example.spring_product_api.repository.Product;
import com.example.spring_product_api.repository.ProductDto;
import com.example.spring_product_api.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAll() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(createResponse(HttpStatus.OK.value(), List.of(), products));
    }

    @GetMapping("{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {
        try {
            Product product = productService.findById(id);
            return ResponseEntity.ok(createResponse(HttpStatus.OK.value(), List.of(), product));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createResponse(HttpStatus.NOT_FOUND.value(), List.of(ex.getMessage()), null));
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductDto productDto) {
        try {
            Product updatedProduct = productService.update(id, productDto);
            return ResponseEntity.ok(createResponse(HttpStatus.OK.value(), List.of(), updatedProduct));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createResponse(HttpStatus.NOT_FOUND.value(), List.of(ex.getMessage()), null));
        } catch (IllegalStateException ex) {
            return ResponseEntity.badRequest()
                    .body(createResponse(HttpStatus.BAD_REQUEST.value(), List.of(ex.getMessage()), null));
        }
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<Map<String, Object>> findByName(@PathVariable String name) {
        List<Product> products = productService.findByNameSubstring(name);
        return ResponseEntity.ok(createResponse(HttpStatus.OK.value(), List.of(), products));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@Valid @RequestBody ProductDto productDto) {
        try {
            Product product = productService.create(productDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(createResponse(HttpStatus.CREATED.value(), List.of(), product));
        } catch (IllegalStateException e) {
            log.error("Ошибка создания продукта: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(createResponse(HttpStatus.BAD_REQUEST.value(), List.of(e.getMessage()), null));
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private Map<String, Object> createResponse(int status, List<String> errors, Object data) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", status);
        response.put("errors", errors);
        response.put("data", data);
        return response;
    }
}*/

package com.example.spring_product_api.controller;

import com.example.spring_product_api.entity.Product;
import com.example.spring_product_api.dto.ProductDto;
import com.example.spring_product_api.service.ProductService;
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

    private Map<String, Object> createResponse(int status, List<String> errors, Object data) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", status);
        response.put("errors", errors);
        response.put("data", data);
        return response;
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
}