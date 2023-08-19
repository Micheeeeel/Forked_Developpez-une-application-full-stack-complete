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
        SubjectDTO subjectDTO1 = new SubjectDTO(1L, "Java", "Lorem ipsum dolor sit amet consectetur adipisicing elit. Fugit, voluptatem mollitia! Quis nesciunt, omnis labore eum nobis, voluptate exercitationem molestias ipsum voluptas aperiam vel hic doloribus? Aut, sit. Aut, ad? ");
        SubjectDTO subjectDTO2 = new SubjectDTO(2L, "JavaScript", "Lorem ipsum dolor sit amet consectetur adipisicing elit. Fugit, voluptatem mollitia! Quis nesciunt, omnis labore eum nobis, voluptate exercitationem molestias ipsum voluptas aperiam vel hic doloribus? Aut, sit. Aut, ad?");
        List<SubjectDTO> subjectDTOs = Arrays.asList(subjectDTO1, subjectDTO2);

        // Define the behavior of the mock subjectService
        Mockito.when(subjectService.getAllSubjects()).thenReturn(subjectDTOs);

        // Perform the HTTP GET request to the API to get all subjects
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subject"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Java"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("JavaScript"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("Lorem ipsum dolor sit amet consectetur adipisicing elit. Fugit, voluptatem mollitia! Quis nesciunt, omnis labore eum nobis, voluptate exercitationem molestias ipsum voluptas aperiam vel hic doloribus? Aut, sit. Aut, ad?"));

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
    public void testDeleteSubjectById_Success() throws Exception {
        Long subjectId = 1L;

        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setId(subjectId);
        subjectDTO.setName("Math");

        Mockito.when(subjectService.getSubjectById(subjectId)).thenReturn(subjectDTO);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/subject/{id}", subjectId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Subject deleted successfully"));
    }

    // test de l'API REST pour supprimer un sujet par son id invalide
    @Test
    public void testDeleteSubjectById_NotFound() throws Exception {
        Long subjectId = 1L;

        Mockito.when(subjectService.getSubjectById(subjectId)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/subject/{id}", subjectId))
        . andExpect(MockMvcResultMatchers.status().isNotFound());
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

   // test de l'API REST pour mettre à jour un sujet existant
   @Test
   public void testUpdateSubject_ValidData_Success() throws Exception {
       // Given
       Long subjectId = 1L;
       SubjectDTO subjectDTO = new SubjectDTO();
       subjectDTO.setName("Math");

       SubjectDTO existingSubjectDTO = new SubjectDTO();
       existingSubjectDTO.setId(subjectId);
       existingSubjectDTO.setName("Science");

       Subject updatedSubject = new Subject();
       updatedSubject.setId(subjectId);
       updatedSubject.setName("Math");

       Mockito.when(subjectService.getSubjectById(subjectId)).thenReturn(existingSubjectDTO);
       Mockito.when(subjectService.updateSubject(Mockito.eq(subjectId), Mockito.any(SubjectDTO.class))).thenReturn(updatedSubject);

       // When
       mockMvc.perform(MockMvcRequestBuilders.put("/api/subject/{id}", subjectId)
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(subjectDTO)))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().string("Subject updated"));

       // Then
       Mockito.verify(subjectService, Mockito.times(1)).getSubjectById(subjectId);
       Mockito.verify(subjectService, Mockito.times(1)).updateSubject(Mockito.eq(subjectId), Mockito.any(SubjectDTO.class));
   }

    // test de l'API REST pour mettre à jour un sujet avec des données invalides
    @Test
    public void testUpdateSubject_InvalidData_Failure() throws Exception {
        // Given
        Long subjectId = 1L;
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setName(""); // Invalid name

        // When
        mockMvc.perform(MockMvcRequestBuilders.put("/api/subject/{id}", subjectId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(subjectDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Invalid Subject data"));

        // Then
        Mockito.verify(subjectService, Mockito.never()).getSubjectById(subjectId);
        Mockito.verify(subjectService, Mockito.never()).updateSubject(Mockito.anyLong(), Mockito.any(SubjectDTO.class));
    }

       // test de l'API REST pour mettre à jour un sujet non existant
       @Test
       public void testUpdateSubject_NonExistingSubject_Failure() throws Exception {
           // Given
           Long subjectId = 1L;
           SubjectDTO subjectDTO = new SubjectDTO();
           subjectDTO.setName("Math");
   
           Mockito.when(subjectService.getSubjectById(subjectId)).thenReturn(null);
   
           // When
           mockMvc.perform(MockMvcRequestBuilders.put("/api/subject/{id}", subjectId)
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(asJsonString(subjectDTO)))
                   .andExpect(MockMvcResultMatchers.status().isNotFound());
   
           // Then
           Mockito.verify(subjectService, Mockito.times(1)).getSubjectById(subjectId);
           Mockito.verify(subjectService, Mockito.never()).updateSubject(Mockito.anyLong(), Mockito.any(SubjectDTO.class));
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
