package com.LibraryManagementSystem.service.impl;

import com.LibraryManagementSystem.entity.Patron;
import com.LibraryManagementSystem.repository.PatronRepository;
import com.LibraryManagementSystem.service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PatronServiceImpl implements PatronService {

    @Autowired
    private PatronRepository patronRepository;

    @Override
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    @Override
    public Patron getPatronById(Long id) {
        return patronRepository.findById(id).orElseThrow(() -> new RuntimeException("Patron not found"));
    }

    @Override
    public Patron createPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    @Override
    public Patron updatePatron(Long id, Patron patron) {
        if (!patronRepository.existsById(id)) {
            throw new RuntimeException("Patron not found");
        }
        Optional<Patron> oldPatron = patronRepository.findById(id);
        patron.setId(id);
        if (patron.getPoints() == null){
            patron.setPoints(oldPatron.get().getPoints());
        }
        return patronRepository.save(patron);
    }

    @Override
    public void deletePatron(Long id) {
        if (!patronRepository.existsById(id)) {
            throw new RuntimeException("Patron not found");
        }
        patronRepository.deleteById(id);
    }

    @Override
    public List<Patron> findByPhoneNumber(String phoneNumber) {
        return patronRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public List<Patron> findByName(String name) {
        return patronRepository.findByName(name);
    }
}

