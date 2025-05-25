package com.example.spring_product_api.repository;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotNull(message = "Price cannot be null")
    private Double price;
    @NotBlank(message = "Description cannot be null")
    private String description;
    private LocalDate orderDateTime;
}