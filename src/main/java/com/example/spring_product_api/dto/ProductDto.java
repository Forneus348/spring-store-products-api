package com.example.spring_product_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotNull(message = "The price cannot be zero")
    private Double price;
    @NotBlank(message = "Description cannot be null")
    private String description;
    private LocalDate orderDateTime;
}