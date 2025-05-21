package com.example.spring_product_api.service;

import com.example.spring_product_api.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseServer findByNameSubstring(String name) {
        try {
            return new ResponseServer(true, HttpStatus.OK, List.of(""), productRepository.findByNameContainingIgnoreCase(name));
        } catch (Exception exception) {
            return new ResponseServer(false, HttpStatus.BAD_REQUEST, List.of("Неизвестная ошибка: ", exception.getMessage()), new Product());
        }
    }

    public ResponseServer findAll() {
        try {
            return new ResponseServer(true, HttpStatus.OK, List.of(""), productRepository.findAll());
        } catch (Exception exception) {
            return new ResponseServer(false, HttpStatus.BAD_REQUEST, List.of("Неизвестная ошибка: ", exception.getMessage()), new Product());
        }
    }

    public ResponseServer findById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Product product = optionalProduct.orElseThrow(() -> new ProductNotFoundException(HttpStatus.NOT_FOUND, "продукт с таким id: " + id + " не найден"));
        return new ResponseServer(true, HttpStatus.OK, List.of(""), product);
    }

    public ResponseServer create(ProductDto productDto) {
        try {
            if (productRepository.findByName(productDto.getName()).isPresent()) {
                return new ResponseServer(false, HttpStatus.BAD_REQUEST, List.of("Продукт с таким именем существует"), new Product());

            }

            Product product = new Product();
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());
            product.setOrderDateTime(LocalDate.now());

            Product savedProduct = productRepository.save(product);
            return new ResponseServer(true, HttpStatus.OK, List.of("Продукт успешно создан"), savedProduct);

        } catch (Exception exception) {
            return new ResponseServer(false, HttpStatus.BAD_REQUEST, List.of("Неизвестная ошибка", exception.getMessage()), new Product());
        }

    }

    public ResponseServer delete(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        Product product = optionalProduct.orElseThrow(() -> new ProductNotFoundException(HttpStatus.NOT_FOUND, "продукт с таким id: " + id + " не найден"));
        productRepository.deleteById(id);

        return new ResponseServer(true, HttpStatus.OK, List.of("Продукт успешно удалён"), new Product());
    }

    @Transactional
    public ResponseServer update(Long id, ProductDto productDto) {
        try {
            Optional<Product> optionalProduct = productRepository.findById(id);

            Product product = optionalProduct.orElseThrow(() -> new ProductNotFoundException(HttpStatus.NOT_FOUND, "продукт с таким id: " + id + " не найден"));

            if (productDto.getName() != null) {
                product.setName(productDto.getName());
            }

            if (productDto.getPrice() != null) {
                product.setPrice(productDto.getPrice());
            }

            if (productDto.getDescription() != null) {
                product.setDescription(productDto.getDescription());
            }

            if (productDto.getOrderDateTime() != null) {
                product.setOrderDateTime(productDto.getOrderDateTime());
            }

            return new ResponseServer(true, HttpStatus.OK, List.of("Продукт успешно обновлён"), product);
        } catch (Exception exception) {
            return new ResponseServer(false, HttpStatus.BAD_REQUEST, List.of("Неизвестная ошибка", exception.getMessage()), new Product());
        }
    }
}