package com.example.spring_product_api.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the public page!";
    }

    @GetMapping("/users")
    public String users() {
        return "Hello, USER!";
    }

    @GetMapping("/admins")
    public String admins() {
        return "Hello, ADMIN!";
    }

    @GetMapping("/all")
    public String all() {
        return "Hello, authenticated user!";
    }
}
