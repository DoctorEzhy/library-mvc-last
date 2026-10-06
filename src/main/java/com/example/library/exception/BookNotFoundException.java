package com.example.library.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long id) {
        super("Книга с номером " + id + " не найдена");
    }
}
