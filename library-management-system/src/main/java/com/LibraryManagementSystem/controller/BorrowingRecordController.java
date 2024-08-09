package com.LibraryManagementSystem.controller;

import com.LibraryManagementSystem.entity.BorrowingRecord;
import com.LibraryManagementSystem.service.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/borrowing-records")
public class BorrowingRecordController {

    @Autowired
    private BorrowingRecordService borrowingRecordService;

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> borrowBook(
            @PathVariable Long bookId,
            @PathVariable Long patronId,
            @RequestParam LocalDate borrowingDate,
            @RequestParam LocalDate returnDate) {
        try {
            BorrowingRecord borrowingRecord = borrowingRecordService.borrowBook(bookId, patronId, borrowingDate, returnDate);
            return new ResponseEntity<>(borrowingRecord, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/return/{recordId}")
    public ResponseEntity<BorrowingRecord> returnBook(
            @PathVariable Long recordId
            ) {
        try {
            BorrowingRecord borrowingRecord = borrowingRecordService.returnBook(recordId);
            return new ResponseEntity<>(borrowingRecord, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
