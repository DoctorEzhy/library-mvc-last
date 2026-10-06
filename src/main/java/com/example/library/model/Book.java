package com.example.library.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Book {

    private Long id;

    @NotBlank(message = "Название обязательно")
    @Size(min = 2, max = 100, message = "Название: от 2 до 100 символов")
    private String title;

    @NotBlank(message = "Автор обязателен")
    @Size(min = 2, max = 60, message = "Автор: от 2 до 60 символов")
    private String author;

    @NotNull(message = "Укажите год издания")
    @Min(value = 1450, message = "Год не может быть раньше 1450")
    @Max(value = 2100, message = "Год не может быть позже 2100")
    private Integer year;

    @Size(max = 500, message = "Описание не длиннее 500 символов")
    private String description;

    @NotNull(message = "Выберите жанр")
    private Long genreId;

    private String genreName;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getGenreId() { return genreId; }
    public void setGenreId(Long genreId) { this.genreId = genreId; }

    public String getGenreName() { return genreName; }
    public void setGenreName(String genreName) { this.genreName = genreName; }
}
