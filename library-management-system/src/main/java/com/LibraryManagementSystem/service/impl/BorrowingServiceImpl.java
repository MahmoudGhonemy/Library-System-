package com.LibraryManagementSystem.service.impl;

import com.LibraryManagementSystem.entity.Book;
import com.LibraryManagementSystem.entity.BorrowingRecord;
import com.LibraryManagementSystem.entity.Patron;
import com.LibraryManagementSystem.repository.BookRepository;
import com.LibraryManagementSystem.repository.BorrowingRecordRepository;
import com.LibraryManagementSystem.repository.PatronRepository;
import com.LibraryManagementSystem.service.BookService;
import com.LibraryManagementSystem.service.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowingServiceImpl implements BorrowingRecordService {

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PatronRepository patronRepository;
    
    @Autowired
    private BookService bookService;

    @Transactional
    @Override
    public BorrowingRecord borrowBook(Long bookId, Long patronId, LocalDate borrowingDate, LocalDate returnDate) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        Optional<Patron> patronOptional = patronRepository.findById(patronId);

        if (bookOptional.isEmpty() || patronOptional.isEmpty()) {
            throw new RuntimeException("Book or Patron not found");
        }

        if (bookOptional.get().getAvailableCopies() <= 0){
            throw new RuntimeException("No available copies of this book");
        }
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(bookOptional.get());
        borrowingRecord.setPatron(patronOptional.get());
        borrowingRecord.setBorrowingDate(borrowingDate);
        borrowingRecord.setReturnDate(returnDate);

        // Calculate and collect fees
        double fees = collectFees(bookOptional.get().getBorrowingPrice(), patronOptional.get());
        borrowingRecord.setCollectedFees(fees);
        
        // Update the available copies
        Book updatedBook = copyBook(bookOptional, -1);

        bookService.updateBook(updatedBook.getId(), updatedBook);

        return borrowingRecordRepository.save(borrowingRecord);
    }

    private static Book copyBook(Optional<Book> bookOptional, int copies) {
        Book updatedBook = new Book();
        updatedBook.setAvailableCopies(bookOptional.get().getAvailableCopies() + copies);
        updatedBook.setId((bookOptional.get().getId()));
        updatedBook.setAuthor(bookOptional.get().getAuthor());
        updatedBook.setIsbn(bookOptional.get().getIsbn());
        updatedBook.setBorrowingPrice(bookOptional.get().getBorrowingPrice());
        updatedBook.setCategory(bookOptional.get().getCategory());
        updatedBook.setTitle(bookOptional.get().getTitle());
        updatedBook.setPublicationYear(bookOptional.get().getPublicationYear());
        return updatedBook;
    }

    @Transactional
    public BorrowingRecord returnBook(Long recordId) {
        Optional<BorrowingRecord> recordOptional = borrowingRecordRepository.findById(recordId);

        if (recordOptional.isEmpty()) {
            throw new RuntimeException("Borrowing Record not found");
        }

        Optional<Book> returnedBook = bookRepository.findById(recordOptional.get().getBook().getId());

        BorrowingRecord borrowingRecord = recordOptional.get();
        Patron patron = borrowingRecord.getPatron();

        // Update return date
        borrowingRecord.setReturnDate(LocalDate.now());

        // Update patron points
        patron.setPoints(patron.getPoints() + 100);
        patronRepository.save(patron);

        // Update the available copies
        Book updatedBook = copyBook(returnedBook, 1);
        bookService.updateBook(updatedBook.getId(), updatedBook);


        return borrowingRecordRepository.save(borrowingRecord);
    }

    @Transactional
    public double collectFees(double borrowingFees, Patron patron) {
        double fees = borrowingFees;

        // Check if patron has more than 1000 points to give free borrowing
        if (patron.getPoints() > 1000) {
            fees = 0; // Borrowing is free
            patron.setPoints(patron.getPoints() - 1000); // Deduct 1000 points
        }

        return fees;
    }

    @Scheduled(cron = "0 0 12 * * ?")
    @Transactional(readOnly = true)
    public void checkReturns() {
        LocalDate today = LocalDate.now();
        List<BorrowingRecord> recordsToReturn = borrowingRecordRepository.findByReturnDate(today);

        for (BorrowingRecord record : recordsToReturn) {
            String message = String.format(
                    "Borrowing Record ID: %d should be returned today, Patron Name: %s, Patron Phone: %s, Patron Gender: %s",
                    record.getId(),
                    record.getPatron().getName(),
                    record.getPatron().getPhoneNumber(),
                    record.getPatron().getGender()
            );
            System.out.println(message);
        }
    }
}
