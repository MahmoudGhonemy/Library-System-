package com.LibraryManagementSystem.service;

import com.LibraryManagementSystem.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAllBooks();
    Optional<Book> getBookById(Long id);
    Book addBook(Book book);
    Book updateBook(Long id, Book book);
    void deleteBook(Long id);
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByCategory(String category);
}
