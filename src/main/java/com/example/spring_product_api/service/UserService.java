package com.example.spring_product_api.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    /*класс для работы с пользователями*/
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
