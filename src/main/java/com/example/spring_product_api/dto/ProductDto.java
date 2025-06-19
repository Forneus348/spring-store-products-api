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
    @NotBlank(message = "Имя не может быть пустым")
    private String name;
    @NotNull(message = "Цена не может быть нулевой")
    private Double price;
    @NotBlank(message = "Описание не может быть нулевым")
    private String description;
    private LocalDate orderDateTime;
}