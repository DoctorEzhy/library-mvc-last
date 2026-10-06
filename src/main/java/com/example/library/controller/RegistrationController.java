package com.example.library.controller;

import com.example.library.model.RegistrationForm;
import com.example.library.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String form(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registrationForm") RegistrationForm form,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
        if (!result.hasFieldErrors("password") && !result.hasFieldErrors("confirmPassword")
                && !form.getPassword().equals(form.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "mismatch", "Пароли не совпадают");
        }
        if (!result.hasFieldErrors("username") && userService.usernameTaken(form.getUsername())) {
            result.rejectValue("username", "duplicate", "Такой логин уже занят");
        }
        if (result.hasErrors()) {
            return "register";
        }
        try {
            userService.register(form.getUsername(), form.getPassword(), "USER");
        } catch (DataIntegrityViolationException e) {
            result.rejectValue("username", "duplicate", "Такой логин уже занят");
            return "register";
        }
        redirectAttributes.addFlashAttribute("message",
                "Регистрация прошла успешно. Откройте раздел «Книги» и введите логин и пароль.");
        return "redirect:/";
    }
}
