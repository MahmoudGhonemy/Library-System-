package com.LibraryManagementSystem.service.impl;

import com.LibraryManagementSystem.entity.Book;
import com.LibraryManagementSystem.exception.BookException;
import com.LibraryManagementSystem.exception.ErrorCode;
import com.LibraryManagementSystem.repository.BookRepository;
import com.LibraryManagementSystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isEmpty()){
            throw new BookException(ErrorCode.BOOK_NOT_FOUND, Map.of("Book ID", id));
        }
        else {
            return book;
        }
    }

    @Transactional
    @Override
    public Book addBook(Book book) {
        bookRepository.findByIsbn(book.getIsbn());
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public Book updateBook(Long id, Book book) {
        if (!bookRepository.existsById(id)) {
            throw new BookException(ErrorCode.BOOK_NOT_FOUND, Map.of("Book ID", id));
        }
        book.setId(id);
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookException(ErrorCode.BOOK_NOT_FOUND, Map.of("Book ID", id));
        }
        bookRepository.deleteById(id);
    }

    @Override
    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    @Override
    public List<Book> findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    public List<Book> findByCategory(String category) {
        return bookRepository.findByCategory(category);
    }
}
