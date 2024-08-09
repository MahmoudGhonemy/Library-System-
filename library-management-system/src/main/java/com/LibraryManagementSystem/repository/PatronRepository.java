package com.LibraryManagementSystem.repository;

import com.LibraryManagementSystem.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PatronRepository extends JpaRepository<Patron, Long> {
    List<Patron> findByPhoneNumber(String phoneNumber);
    List<Patron> findByName(String name);
}

