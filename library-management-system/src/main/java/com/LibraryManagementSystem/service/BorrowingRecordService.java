package com.LibraryManagementSystem.service;

import com.LibraryManagementSystem.entity.BorrowingRecord;

import java.time.LocalDate;

public interface BorrowingRecordService {
    BorrowingRecord borrowBook(Long bookId, Long patronId, LocalDate borrowingDate, LocalDate returnDate);
    BorrowingRecord returnBook(Long recordId);
}
