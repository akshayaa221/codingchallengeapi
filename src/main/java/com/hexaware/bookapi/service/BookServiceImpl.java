package com.hexaware.bookapi.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.hexaware.bookapi.exception.ResourceNotFoundException;
import com.hexaware.bookapi.model.Book;
import com.hexaware.bookapi.repository.BookRepository;

import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository repo;

    public BookServiceImpl(BookRepository repo) {
        this.repo = repo;
    }

    public List<Book> getAllBooks() {
        return repo.findAll();
    }

    public Book getBookByIsbn(String isbn) {
        return repo.findById(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "ISBN", isbn));
    }

    public Book createBook(Book book) {
        return repo.save(book);
    }

    public Book updateBook(String isbn, Book book) {
        Book existing = getBookByIsbn(isbn);
        existing.setTitle(book.getTitle());
        existing.setAuthor(book.getAuthor());
        existing.setPublicationYear(book.getPublicationYear());
        return repo.save(existing);
    }

    public void deleteBook(String isbn) {
        Book existing = getBookByIsbn(isbn);
        repo.delete(existing);
    }
}

