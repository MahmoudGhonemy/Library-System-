package com.LibraryManagementSystem.Service;

import com.LibraryManagementSystem.entity.Book;
import com.LibraryManagementSystem.entity.BorrowingRecord;
import com.LibraryManagementSystem.entity.Patron;
import com.LibraryManagementSystem.repository.BookRepository;
import com.LibraryManagementSystem.repository.BorrowingRecordRepository;
import com.LibraryManagementSystem.repository.PatronRepository;
import com.LibraryManagementSystem.service.impl.BorrowingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@SpringBootTest
public class BorrowingServiceTest {

    @Autowired
    private BorrowingServiceImpl borrowingServiceImpl;

    @MockBean
    private BorrowingRecordRepository borrowingRecordRepository;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private PatronRepository patronRepository;

    private Book book;
    private Patron patron;

    @BeforeEach
    public void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Book Title");
        book.setAuthor("Author");
        book.setIsbn("12345");
        book.setBorrowingPrice(10.0);
        book.setCategory("Category");

        patron = new Patron();
        patron.setId(1L);
        patron.setName("Patron Name");
        patron.setPhoneNumber("123456789");
        patron.setPoints(0);
        patron.setGender("Male");
    }

    @Test
    public void testBorrowBook_Success() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        BorrowingRecord borrowingRecord = borrowingServiceImpl.borrowBook(1L, 1L, LocalDate.now(), LocalDate.now().plusDays(7));

        // Assert
        assertNotNull(borrowingRecord);
        assertEquals(10.0, borrowingRecord.getCollectedFees());
        verify(borrowingRecordRepository, times(1)).save(borrowingRecord);
    }

    @Test
    public void testBorrowBook_BookNotFound() {
        // Arrange
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            borrowingServiceImpl.borrowBook(1L, 1L, LocalDate.now(), LocalDate.now().plusDays(7));
        });
        assertEquals("Book or Patron not found", thrown.getMessage());
    }

    @Test
    public void testReturnBook_Success() {
        // Arrange
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setId(1L);
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowingDate(LocalDate.now().minusDays(7));
        borrowingRecord.setReturnDate(null);

        when(borrowingRecordRepository.findById(1L)).thenReturn(Optional.of(borrowingRecord));
        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(patronRepository.save(any(Patron.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        BorrowingRecord returnedRecord = borrowingServiceImpl.returnBook(1L);

        // Assert
        assertNotNull(returnedRecord);
        assertEquals(LocalDate.now(), returnedRecord.getReturnDate());
        assertEquals(100, patron.getPoints()); // 100 points added
    }

    @Test
    public void testCollectFees_PatronHasEnoughPoints() {
        // Adjust the patron points
        patron.setPoints(1100);

        // Arrange
        double borrowingPrice = 10.0;

        // Act
        double fees = borrowingServiceImpl.collectFees(borrowingPrice, patron);

        // Assert
        assertEquals(0.0, fees);
        assertEquals(100, patron.getPoints()); // 1000 points deducted

        // Reset Points
        patron.setPoints(0);
    }

    @Test
    public void testCollectFees_PatronNotEnoughPoints() {
        // Arrange
        patron.setPoints(500); // less than 1000 points
        double borrowingPrice = 10.0;

        // Act
        double fees = borrowingServiceImpl.collectFees(borrowingPrice, patron);

        // Assert
        assertEquals(10.0, fees);
        assertEquals(500, patron.getPoints()); // points unchanged
    }

    @Test
    public void testCheckReturns() {
        // Arrange
        BorrowingRecord record = new BorrowingRecord();
        record.setId(1L);
        record.setBook(book);
        record.setPatron(patron);
        record.setBorrowingDate(LocalDate.now().minusDays(7));
        record.setReturnDate(LocalDate.now());

        when(borrowingRecordRepository.findByReturnDate(LocalDate.now())).thenReturn(List.of(record));

        // Act
        borrowingServiceImpl.checkReturns();

        // Assert
        // Since checkReturns() only prints to console, we can't assert the console output directly.
        // We might need to use a logging framework or capture the console output for assertion.
    }
}
