package com.LibraryManagementSystem.Service;

import com.LibraryManagementSystem.entity.Patron;
import com.LibraryManagementSystem.repository.PatronRepository;
import com.LibraryManagementSystem.service.impl.PatronServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

@SpringBootTest
public class PatronServiceTest {

    @Autowired
    private PatronServiceImpl patronServiceImpl;

    @MockBean
    private PatronRepository patronRepository;

    private Patron patron;

    @BeforeEach
    public void setUp() {
        patron = new Patron();
        patron.setId(1L);
        patron.setName("John Doe");
        patron.setPhoneNumber("1234567890");
        patron.setPoints(100);
        patron.setGender("Male");
    }

    @Test
    public void testGetAllPatrons() {
        // Arrange
        List<Patron> patrons = new ArrayList<>();
        patrons.add(patron);
        when(patronRepository.findAll()).thenReturn(patrons);

        // Act
        List<Patron> result = patronServiceImpl.getAllPatrons();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(patron.getName(), result.get(0).getName());
    }

    @Test
    public void testGetPatronById_Success() {
        // Arrange
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));

        // Act
        Patron result = patronServiceImpl.getPatronById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(patron.getName(), result.getName());
    }

    @Test
    public void testGetPatronById_NotFound() {
        // Arrange
        when(patronRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            patronServiceImpl.getPatronById(1L);
        });
        assertEquals("Patron not found", thrown.getMessage());
    }

    @Test
    public void testCreatePatron() {
        // Arrange
        when(patronRepository.save(patron)).thenReturn(patron);

        // Act
        Patron result = patronServiceImpl.createPatron(patron);

        // Assert
        assertNotNull(result);
        assertEquals(patron.getName(), result.getName());
    }

    @Test
    public void testUpdatePatron_Success() {
        // Arrange
        Patron updatedPatron = new Patron();
        updatedPatron.setId(1L);
        updatedPatron.setName("Jane Doe");
        updatedPatron.setPhoneNumber("0987654321");
        updatedPatron.setPoints(200);
        updatedPatron.setGender("Female");

        when(patronRepository.existsById(1L)).thenReturn(true);
        when(patronRepository.save(updatedPatron)).thenReturn(updatedPatron);

        // Act
        Patron result = patronServiceImpl.updatePatron(1L, updatedPatron);

        // Assert
        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        assertEquals("0987654321", result.getPhoneNumber());
    }

    @Test
    public void testUpdatePatron_NotFound() {
        // Arrange
        when(patronRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            patronServiceImpl.updatePatron(1L, patron);
        });
        assertEquals("Patron not found", thrown.getMessage());
    }

    @Test
    public void testDeletePatron_Success() {
        // Arrange
        when(patronRepository.existsById(1L)).thenReturn(true);

        // Act
        patronServiceImpl.deletePatron(1L);

        // Assert
        verify(patronRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeletePatron_NotFound() {
        // Arrange
        when(patronRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            patronServiceImpl.deletePatron(1L);
        });
        assertEquals("Patron not found", thrown.getMessage());
    }

    @Test
    public void testFindByPhoneNumber() {
        // Arrange
        when(patronRepository.findByPhoneNumber("1234567890")).thenReturn(List.of(patron));

        // Act
        List<Patron> result = patronServiceImpl.findByPhoneNumber("1234567890");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(patron.getName(), result.get(0).getName());
    }

    @Test
    public void testFindByName() {
        // Arrange
        when(patronRepository.findByName("John Doe")).thenReturn(List.of(patron));

        // Act
        List<Patron> result = patronServiceImpl.findByName("John Doe");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(patron.getPhoneNumber(), result.get(0).getPhoneNumber());
    }
}
