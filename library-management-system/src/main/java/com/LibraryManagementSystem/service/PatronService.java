package com.LibraryManagementSystem.service;

import com.LibraryManagementSystem.entity.Patron;
import java.util.List;

public interface PatronService {
    List<Patron> getAllPatrons();
    Patron getPatronById(Long id);
    Patron createPatron(Patron patron);
    Patron updatePatron(Long id, Patron patron);
    void deletePatron(Long id);
    List<Patron> findByPhoneNumber(String phoneNumber);
    List<Patron> findByName(String name);
}
