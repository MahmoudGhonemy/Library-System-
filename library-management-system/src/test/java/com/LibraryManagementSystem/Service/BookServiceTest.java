package com.LibraryManagementSystem.Service;

import com.LibraryManagementSystem.entity.Book;
import com.LibraryManagementSystem.repository.BookRepository;
import com.LibraryManagementSystem.service.BookService;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    private Book book;

    @BeforeEach
    public void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Book Title");
        book.setAuthor("Author");
        book.setIsbn("12345");
        book.setBorrowingPrice(12);
        book.setCategory("category");
    }

    @Test
    public void testFindByAuthor() {
        when(bookRepository.findByAuthor("Author")).thenReturn(List.of(book));

        List<Book> found = bookService.findByAuthor("Author");
        assertNotNull(found);
        assertEquals(book.getTitle(), found.get(0).getTitle());
    }

    @Test
    public void testFindByIsbn() {
        when(bookRepository.findByIsbn("12345")).thenReturn(List.of(book));

        List<Book> found = bookService.findByIsbn("12345");
        assertNotNull(found);
        assertEquals(book.getIsbn(), found.get(0).getIsbn());
    }

    @Test
    public void testSaveBook() {
        when(bookRepository.save(book)).thenReturn(book);

        Book savedBook = bookService.addBook(book);
        assertNotNull(savedBook);
        assertEquals(book.getTitle(), savedBook.getTitle());
    }

    @Test
    public void testUpdateBook() {
        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setTitle("Updated Title");
        updatedBook.setAuthor("Updated Author");
        updatedBook.setIsbn("54321");
        updatedBook.setBorrowingPrice(15);
        updatedBook.setCategory("Updated Category");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.existsById(1L)).thenReturn(true);
        when(bookRepository.save(updatedBook)).thenReturn(updatedBook);

        Book result = bookService.updateBook(1L, updatedBook);
        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Author", result.getAuthor());
    }

    @Test
    public void testFindAllBooks() {
        Book anotherBook = new Book();
        anotherBook.setId(2L);
        anotherBook.setTitle("Another Book Title");
        anotherBook.setAuthor("Another Author");
        anotherBook.setIsbn("67890");
        anotherBook.setBorrowingPrice(15);
        anotherBook.setCategory("Another Category");

        when(bookRepository.findAll()).thenReturn(List.of(book, anotherBook));

        List<Book> books = bookService.getAllBooks();
        assertNotNull(books);
        assertEquals(2, books.size());
        assertEquals("Book Title", books.get(0).getTitle());
        assertEquals("Another Book Title", books.get(1).getTitle());
    }
}
