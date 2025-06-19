package com.example.spring_product_api.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String username;
    private String password;
}
