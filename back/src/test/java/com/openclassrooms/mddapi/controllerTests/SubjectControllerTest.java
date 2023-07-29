package com.openclassrooms.mddapi.controllerTests;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.mddapi.dto.SubjectDTO;
import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.service.SubjectService;

import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

@AutoConfigureMockMvc
@SpringBootTest
public class SubjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

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

    // test de l'API REST pour créer un nouveau sujet
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

    // test de l'API REST pour supprimer un sujet par son id
    @Test
    public void testDeleteSubjectById_ValidId_ReturnsOk() throws Exception {
        // Given
        Long subjectId = 1L;

        // When
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/subject/{id}", subjectId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // Then
        verify(subjectService, times(1)).deleteSubjectById(subjectId);
    }

        @Test
    public void testDeleteSubjectById_InvalidId_ReturnsNotFound() throws Exception {
        // Given
        Long subjectId = 1L;

        // Configure the mock service to throw an exception when deleteSubjectById is called
        doThrow(new RuntimeException()).when(subjectService).deleteSubjectById(subjectId);

        // When
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/subject/{id}", subjectId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        // Then
        verify(subjectService, times(1)).deleteSubjectById(subjectId);
    }


    // test de l'API REST pour récupérer un sujet par son id
    @Test
    public void testGetSubjectById_ValidId_ReturnsSubject() throws Exception {
        // Given
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setName("Math");

        // Define the behavior of the mock subjectService
        Mockito.when(subjectService.getSubjectById(1L)).thenReturn(subjectDTO);

        // Perform the HTTP GET request to the API to get one subject
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subject/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Math"));

        // Then
        Mockito.verify(subjectService, Mockito.times(1)).getSubjectById(1L);
    }
        
    // test de l'API REST pour récupéré un sujet par son id invalide
    @Test
    public void testGetSubjectById_InvalidId_ReturnsNotFound() throws Exception {
        // Given
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setName("Math");

        // Define the behavior of the mock subjectService
        Long subjectId = 1L;
        Mockito.when(subjectService.getSubjectById(subjectId)).thenReturn(null);

        // Perform the HTTP GET request to the API to get one subject
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subject/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());    // expect a 404 since the subject with id 1 doesn't exist

        // Then
        Mockito.verify(subjectService, Mockito.times(1)).getSubjectById(subjectId);
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
