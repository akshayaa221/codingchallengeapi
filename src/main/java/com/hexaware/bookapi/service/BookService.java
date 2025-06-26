package com.hexaware.bookapi.service;

import java.util.List;
import com.hexaware.bookapi.model.Book;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookByIsbn(String isbn);
    Book createBook(Book book);
    Book updateBook(String isbn, Book book);
    void deleteBook(String isbn);
}
