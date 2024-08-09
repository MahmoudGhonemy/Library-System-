package com.LibraryManagementSystem.controller;

import com.LibraryManagementSystem.entity.Patron;
import com.LibraryManagementSystem.service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    @Autowired
    private PatronService patronService;

    @GetMapping
    public List<Patron> getAllPatrons() {
        return patronService.getAllPatrons();
    }

    @GetMapping("/{id}")
    public Patron getPatronById(@PathVariable Long id) {
        return patronService.getPatronById(id);
    }

    @PostMapping
    public Patron createPatron(@RequestBody Patron patron) {
        return patronService.createPatron(patron);
    }

    @PutMapping("/{id}")
    public Patron updatePatron(@PathVariable Long id, @RequestBody Patron patron) {
        return patronService.updatePatron(id, patron);
    }

    @DeleteMapping("/{id}")
    public void deletePatron(@PathVariable Long id) {
        patronService.deletePatron(id);
    }

    @GetMapping("/search/phone")
    public List<Patron> findByPhoneNumber(@RequestParam String phoneNumber) {
        return patronService.findByPhoneNumber(phoneNumber);
    }

    @GetMapping("/search/name")
    public List<Patron> findByName(@RequestParam String name) {
        return patronService.findByName(name);
    }
}

