package com.example.library.service;

import com.example.library.entity.AuthorEntity;
import com.example.library.entity.BookEntity;
import com.example.library.entity.GenreEntity;
import com.example.library.exception.BookNotFoundException;
import com.example.library.model.Book;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import com.example.library.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Autowired
    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository,
                       GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAllByOrderByIdAsc().stream().map(this::toModel).toList();
    }

    @Transactional(readOnly = true)
    public List<Book> search(String query) {
        if (query == null || query.isBlank()) {
            return findAll();
        }
        String pattern = "%" + query.trim().toLowerCase() + "%";
        return bookRepository.search(pattern).stream().map(this::toModel).toList();
    }

    @Transactional(readOnly = true)
    public Book getById(Long id) {
        return toModel(findEntity(id));
    }

    @Transactional
    public Book create(Book form) {
        BookEntity entity = new BookEntity();
        applyForm(entity, form);
        return toModel(bookRepository.save(entity));
    }

    @Transactional
    public Book update(Long id, Book form) {
        BookEntity entity = findEntity(id);
        AuthorEntity oldAuthor = entity.getAuthor();
        applyForm(entity, form);
        bookRepository.saveAndFlush(entity);
        removeAuthorIfUnused(oldAuthor);
        return toModel(entity);
    }

    @Transactional
    public void delete(Long id) {
        BookEntity entity = findEntity(id);
        AuthorEntity author = entity.getAuthor();
        bookRepository.delete(entity);
        bookRepository.flush();
        removeAuthorIfUnused(author);
    }

    @Transactional(readOnly = true)
    public long count() {
        return bookRepository.count();
    }

    @Transactional(readOnly = true)
    public Map<String, Long> countBooksByAuthor() {
        Map<String, Long> result = new TreeMap<>();
        for (BookEntity book : bookRepository.findAllByOrderByIdAsc()) {
            result.merge(book.getAuthor().getName(), 1L, Long::sum);
        }
        return result;
    }

    @Transactional(readOnly = true)
    public List<GenreEntity> findAllGenres() {
        return genreRepository.findAllByOrderByNameAsc();
    }

    private BookEntity findEntity(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    private void applyForm(BookEntity entity, Book form) {
        entity.setTitle(form.getTitle().trim());
        entity.setYear(form.getYear());
        entity.setDescription(form.getDescription() == null || form.getDescription().isBlank()
                ? null : form.getDescription().trim());
        entity.setAuthor(findOrCreateAuthor(form.getAuthor().trim()));
        entity.setGenre(genreRepository.findById(form.getGenreId())
                .orElseThrow(() -> new IllegalArgumentException("Жанр не найден: " + form.getGenreId())));
    }

    private AuthorEntity findOrCreateAuthor(String name) {
        return authorRepository.findByNameIgnoreCase(name)
                .orElseGet(() -> authorRepository.save(new AuthorEntity(name)));
    }

    private void removeAuthorIfUnused(AuthorEntity author) {
        if (!bookRepository.existsByAuthor(author)) {
            authorRepository.delete(author);
        }
    }

    private Book toModel(BookEntity entity) {
        Book book = new Book();
        book.setId(entity.getId());
        book.setTitle(entity.getTitle());
        book.setAuthor(entity.getAuthor().getName());
        book.setYear(entity.getYear());
        book.setDescription(entity.getDescription());
        book.setGenreId(entity.getGenre().getId());
        book.setGenreName(entity.getGenre().getName());
        return book;
    }
}
