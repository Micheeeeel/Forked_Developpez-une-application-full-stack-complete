package com.openclassrooms.mddapi.IST;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.mddapi.dto.SubjectDTO;
import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import com.openclassrooms.mddapi.service.SubjectService;

@SpringBootTest
@AutoConfigureMockMvc
public class SubjectControllerIntegrationTest {

    // IMPORTANT: make sure to activate MySQL service before running this test

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SubjectRepository subjectRepository;

    // Nétoyage de la base de données avant chaque test
    @BeforeEach
    public void clearDatabase() {
        subjectRepository.deleteAll();
    }

    @Test
    public void testGetAllSubjects() throws Exception {
        // Insert test subjects into the database
        Subject subject1 = new Subject("Java");
        Subject subject2 = new Subject("JavaScript");
        subjectRepository.save(subject1);
        subjectRepository.save(subject2);

        // Perform the HTTP GET request to the API to get all subjects
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subject"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Java"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("JavaScript"));
    }

    @Test
    public void testCreateSubject() throws Exception {
        // Prepare the request body
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setName("Java");

        // Perform the HTTP POST request to create a subject
        mockMvc.perform(MockMvcRequestBuilders.post("/api/subject")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(subjectDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("New Subject created"));

        // Verify that the subject was created in the database
        List<Subject> subjects = subjectRepository.findAll();
        assertEquals(1, subjects.size());
        assertEquals("Java", subjects.get(0).getName());
    }

    @Test
    public void testCreateSubjectWithInvalidData() throws Exception {
        // Prepare the request body with invalid data (empty name)
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setName("");

        // Perform the HTTP POST request to create a subject with invalid data
        mockMvc.perform(MockMvcRequestBuilders.post("/api/subject")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(subjectDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Invalid Subject data"));

        // Verify that no subject was created in the database
        List<Subject> subjects = subjectRepository.findAll();
        assertEquals(0, subjects.size());
    }

    // Utility method to convert object to JSON string
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
