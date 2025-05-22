package com.example.spring_product_api.controller;

import com.example.spring_product_api.repository.Product;
import com.example.spring_product_api.repository.ProductDto;
import com.example.spring_product_api.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/products")
public class ProductController {
    @Autowired
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/search/{name}")
    public ResponseEntity<List<Product>> findByRegistr(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findByNameSubstring(name));
    }


    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAll());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Product> findById(Long id) {
        Product product = productService.findById(id);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Product());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody ProductDto productDto) {
        Product product = productService.create(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Product> delete(@PathVariable(name = "id") Long id) {
        Product product = productService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Product> update(
            @PathVariable Long id, ProductDto productDto
    ) {
        Product product = productService.findById(id);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Product());
        } else {
            Product productUpdate = productService.update(id, productDto);
            return ResponseEntity.status(HttpStatus.OK).body(productUpdate);
        }
    }


}