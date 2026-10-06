package com.example.library.controller;

import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthorController {

    private final BookService bookService;

    @Autowired
    public AuthorController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/authors")
    public String authors(Model model) {
        model.addAttribute("authors", bookService.countBooksByAuthor());
        return "authors/list";
    }
}
