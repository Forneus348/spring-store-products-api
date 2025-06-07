/*package com.example.spring_product_api.service;

import com.example.spring_product_api.repository.Product;
import com.example.spring_product_api.repository.ProductDto;
import com.example.spring_product_api.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }

    @Transactional
    public Product update(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        productRepository.findByName(productDto.getName())
                .ifPresent(p -> {
                    if (!p.getId().equals(id)) {
                        throw new IllegalStateException("Product name already exists: " + productDto.getName());
                    }
                });

        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setOrderDateTime(productDto.getOrderDateTime());

        return productRepository.save(product);
    }

    public Product create(ProductDto productDto) {
        productRepository.findByName(productDto.getName())
                .ifPresent(p -> {
                    throw new IllegalStateException("Product already exists: " + productDto.getName());
                });

        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setOrderDateTime(productDto.getOrderDateTime());

        return productRepository.save(product);
    }

    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        productRepository.delete(product);
    }

    public List<Product> findByNameSubstring(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
}*/

package com.example.spring_product_api.service;

import com.example.spring_product_api.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findByNameSubstring(String name) {
        try {
            return productRepository.findByNameContainingIgnoreCase(name);
        } catch (Exception exception) {
            return List.of(new Product());
        }
    }

    public List<Product> findAll() {
        try {
            return productRepository.findAll();
        } catch (Exception exception) {
            return List.of(new Product());
        }
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null); // Возвращаем null, если не найден
    }

    public Product create(ProductDto productDto) {
        try {
            if (productRepository.findByName(productDto.getName()).isPresent()) {
                return null;
            }

            Product product = new Product();
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());
            product.setOrderDateTime(LocalDate.now());

            return productRepository.save(product);

        } catch (Exception exception) {
            System.err.println("Ошибка при создании продукта: " + exception.getMessage());
            return null;
        }
    }

    public Product delete(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) return new Product();
        Product product = optionalProduct.get();
        productRepository.deleteById(id);

        return product;
    }

    @Transactional
    public Product update(@NotNull Long id, @Valid ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setOrderDateTime(productDto.getOrderDateTime());

        return productRepository.save(product);
    }
}