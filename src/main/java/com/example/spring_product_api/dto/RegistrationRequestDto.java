package com.example.spring_product_api.dto;

import lombok.Data;

@Data
public class RegistrationRequestDto {
    private String username;
    private String email;
    private String password;
}
