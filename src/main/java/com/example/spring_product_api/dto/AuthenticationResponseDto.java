package com.example.spring_product_api.dto;

import lombok.Getter;

@Getter
public class AuthenticationResponseDto {
    private final String accessToken;
    private final String refreshToken;

    public AuthenticationResponseDto(String token, String refreshToken) {
        this.accessToken = token;
        this.refreshToken = refreshToken;
    }
}
