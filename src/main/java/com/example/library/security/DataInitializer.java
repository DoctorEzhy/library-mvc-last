package com.example.library.security;

import com.example.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;

    @Autowired
    public DataInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        createIfAbsent("admin", "admin123", "ADMIN");
        createIfAbsent("user", "user123", "USER");
        System.out.println("Пользователи по умолчанию: admin / admin123 (ADMIN), user / user123 (USER)");
    }

    private void createIfAbsent(String username, String password, String role) {
        if (!userService.usernameTaken(username)) {
            userService.register(username, password, role);
        }
    }
}
