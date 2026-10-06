package com.example.library.controller;

import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final BookService bookService;

    @Autowired
    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("bookCount", bookService.count());
        model.addAttribute("authorCount", bookService.countBooksByAuthor().size());
        return "index";
    }

    @GetMapping("/error-test")
    public String errorTest() {
        throw new IllegalStateException("Тестовая ошибка для проверки страницы 500");
    }
}
