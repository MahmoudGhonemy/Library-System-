package com.LibraryManagementSystem.controller;

import com.LibraryManagementSystem.entity.Patron;
import com.LibraryManagementSystem.service.PatronService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(PatronController.class)
@ActiveProfiles("test")
public class PatronControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatronService patronService;

    @Autowired
    private ObjectMapper objectMapper;

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
    @WithMockUser(username = "user", roles = {"Admin"})
    public void testGetAllPatrons() throws Exception {
        // Arrange
        when(patronService.getAllPatrons()).thenReturn(List.of(patron));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/patrons")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"Admin"})
    public void testGetPatronById_Success() throws Exception {
        // Arrange
        when(patronService.getPatronById(anyLong())).thenReturn(patron);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/patrons/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"Admin"})
    public void testGetPatronById_NotFound() throws Exception {
        // Arrange
        when(patronService.getPatronById(anyLong())).thenThrow(new RuntimeException("Patron not found"));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/patrons/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().string("Patron not found"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"Admin"})
    public void testFindByPhoneNumber() throws Exception {
        // Arrange
        when(patronService.findByPhoneNumber("1234567890")).thenReturn(List.of(patron));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/patrons/search/phone")
                        .param("phoneNumber", "1234567890")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"Admin"})
    public void testFindByName() throws Exception {
        // Arrange
        when(patronService.findByName("John Doe")).thenReturn(List.of(patron));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/patrons/search/name")
                        .param("name", "John Doe")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].phoneNumber").value("1234567890"));
    }
}
