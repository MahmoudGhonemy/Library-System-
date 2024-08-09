package com.LibraryManagementSystem.repository;

import com.LibraryManagementSystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByCategory(String category);

    // Custom query methods can be defined here if needed
}