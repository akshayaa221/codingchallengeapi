package com.hexaware.bookapi.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hexaware.bookapi.model.Book;
import com.hexaware.bookapi.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService service;

    @GetMapping
    public List<Book> getAll() {
        return service.getAllBooks();
    }

    @GetMapping("/{isbn}")
    public Book getByIsbn(@PathVariable String isbn) {
        return service.getBookByIsbn(isbn);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<Book> create(@Valid @RequestBody Book book) {
        Book created = service.createBook(book);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{isbn}")
    public Book update(@PathVariable String isbn, @Valid @RequestBody Book book) {
        return service.updateBook(isbn, book);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{isbn}")
    public ResponseEntity<?> delete(@PathVariable String isbn) {
        service.deleteBook(isbn);
        return ResponseEntity.noContent().build();
    }
}
