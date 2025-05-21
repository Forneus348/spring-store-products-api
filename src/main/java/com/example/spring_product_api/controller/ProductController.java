package com.example.spring_product_api.controller;

import com.example.spring_product_api.repository.Product;
import com.example.spring_product_api.repository.ProductDto;
import com.example.spring_product_api.repository.ResponseServer;
import com.example.spring_product_api.service.ProductService;
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
    public ResponseEntity<ResponseServer> findByRegistr(@PathVariable String name) {
//        List<Product> products = productService.findByNameSubstring(name);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseServer(true, HttpStatus.OK, List.of(""), productService.findByNameSubstring(name)));
    }


    @GetMapping
    public ResponseEntity<ResponseServer> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseServer(true, HttpStatus.OK, List.of(""), productService.findAll()));
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<ResponseServer> findById(Long id) {
        ResponseServer response = productService.findById(id);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseServer(false, HttpStatus.NOT_FOUND, List.of("продукта с таким id не существует"), new Product()));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseServer(true, HttpStatus.OK, List.of(""), response));
        }
    }

    @PostMapping
    public ResponseEntity<ResponseServer> create(@RequestBody ProductDto productDto) {
        ResponseServer response = productService.create(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<ResponseServer> delete(@PathVariable(name = "id") Long id) {
        ResponseServer response = productService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<ResponseServer> update(
            @PathVariable Long id, ProductDto productDto
    ) {
        ResponseServer response = productService.findById(id);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseServer(false, HttpStatus.NOT_FOUND, List.of("продукта с таким id не существует"), new Product()));
        } else {
            ResponseServer responseUpdate = productService.update(id, productDto);
            return ResponseEntity.status(HttpStatus.OK).body(responseUpdate);
        }
    }

}