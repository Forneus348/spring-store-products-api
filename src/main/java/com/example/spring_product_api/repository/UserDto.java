package com.example.spring_product_api.repository;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    @NotBlank(message = "Username cannot be blank")
    private String username;
    @NotNull(message = "Email cannot be null")
    private String email;
    @NotBlank(message = "Password cannot be null")
    private String password;
}
