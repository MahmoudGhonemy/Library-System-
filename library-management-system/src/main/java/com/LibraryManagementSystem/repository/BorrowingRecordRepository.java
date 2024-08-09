package com.LibraryManagementSystem.repository;

import com.LibraryManagementSystem.entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {

    List<BorrowingRecord> findByReturnDate(LocalDate returnDate);
}
