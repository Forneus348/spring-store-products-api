package com.example.spring_product_api.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
    @PositiveOrZero(message = "Price must be positive or zero")
    private String description;
    private LocalDate orderDateTime;
}