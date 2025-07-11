package com.example.user_service.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    /*класс для работы с пользователями*/
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
