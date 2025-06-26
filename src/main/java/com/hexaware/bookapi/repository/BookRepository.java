package com.hexaware.bookapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hexaware.bookapi.model.Book;

public interface BookRepository extends JpaRepository<Book, String> { }
