package com.example.spring_product_api.service;

import com.example.spring_product_api.dto.ProductDto;
import com.example.spring_product_api.entity.Product;
import com.example.spring_product_api.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
        return productRepository.findById(id).orElse(null);
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
            System.err.println("Error creating product: " + exception.getMessage());
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
                .orElseThrow(() -> new EntityNotFoundException("Product id not found: " + id));
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setOrderDateTime(productDto.getOrderDateTime());

        return productRepository.save(product);
    }
}