package com.example.library.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegistrationForm {

    @NotBlank(message = "Введите логин")
    @Size(min = 3, max = 30, message = "Логин: от 3 до 30 символов")
    @Pattern(regexp = "[A-Za-z0-9_]*", message = "Логин: только латинские буквы, цифры и _")
    private String username;

    @NotBlank(message = "Введите пароль")
    @Size(min = 6, max = 72, message = "Пароль: от 6 до 72 символов")
    private String password;

    @NotBlank(message = "Повторите пароль")
    private String confirmPassword;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
}
