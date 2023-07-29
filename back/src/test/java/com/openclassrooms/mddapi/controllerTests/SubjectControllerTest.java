package com.openclassrooms.mddapi.controllerTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import java.util.Arrays;
import java.util.List;

@AutoConfigureMockMvc
@SpringBootTest
public class SubjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SubjectRepository subjectRepository;

    @MockBean
    private SubjectService subjectService;


    // test de l'API REST pour récupérer l'ensemble des sujets
    @Test
    public void testGetAllSubjects() throws Exception {
        // Create test SubjectDTO objects
        SubjectDTO subjectDTO1 = new SubjectDTO(1L, "Java");
        SubjectDTO subjectDTO2 = new SubjectDTO(2L, "JavaScript");
        List<SubjectDTO> subjectDTOs = Arrays.asList(subjectDTO1, subjectDTO2);

        // Define the behavior of the mock subjectService
        Mockito.when(subjectService.getAllSubjects()).thenReturn(subjectDTOs);

        // Perform the HTTP GET request to the API to get all subjects
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subject"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Java"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("JavaScript"));
    }


     @Test
    public void testCreateSubject_ValidData_ReturnsNewSubjectCreated() throws Exception {
        // Given
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setName("Math");
        
        // plutot que d'utiliser un in-memory database, on utilise un mock pour simplifier et accélérer le test
        Subject createdSubject = new Subject();
        createdSubject.setId(1L);
        createdSubject.setName("Math");

        Mockito.when(subjectService.createSubject(Mockito.any(SubjectDTO.class))).thenReturn(createdSubject);

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/api/subject")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(subjectDTO))) // convertir le sujetDTO en JSON tel qu'il serait envoyé par le client
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("New Subject created"));

        // Then
        Mockito.verify(subjectService, Mockito.times(1)).createSubject(Mockito.any(SubjectDTO.class));
    }

    @Test
    public void testCreateSubject_InvalidData_ReturnsInvalidSubjectData() throws Exception {
        // Given
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setName(""); // Invalid name

        // When
        mockMvc.perform(MockMvcRequestBuilders.post("/api/subject")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(subjectDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Invalid Subject data"));

        // Then
        Mockito.verify(subjectService, Mockito.never()).createSubject(Mockito.any(SubjectDTO.class));
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
