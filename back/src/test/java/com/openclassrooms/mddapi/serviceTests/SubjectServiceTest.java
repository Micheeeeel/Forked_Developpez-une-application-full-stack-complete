package com.openclassrooms.mddapi.serviceTests;

import static org.junit.jupiter.api.Assertions.*;

import com.openclassrooms.mddapi.dto.SubjectDTO;
import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import com.openclassrooms.mddapi.service.SubjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SubjectServiceTest {

    @Mock   // Créer un mock de SubjectRepository
    private SubjectRepository subjectRepository;

    @InjectMocks    // Injecter le mock SubjectRepository dans le service
    private SubjectService subjectService;

    // Test de la méthode createSubject du service
    @Test
    public void testCreateSubject() {
        // Créer un nouveau sujetDTO
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setName("Java");

        // Créer une entité Subject simulée qui sera renvoyée par la méthode save du mock SubjectRepository
        Subject simulatedSubject = new Subject();
        simulatedSubject.setId(1L);
        simulatedSubject.setName("Java");

        // Configurer le mock SubjectRepository pour renvoyer la valeur simulée lors de l'appel à la méthode save
        when(subjectRepository.save(any(Subject.class))).thenReturn(simulatedSubject);

        // Appeler la méthode createSubject du service avec le sujetDTO
        Subject createdSubject = subjectService.createSubject(subjectDTO);

        // Vérifier que le sujet a été enregistré avec succès et qu'il possède un ID
        assertNotNull(createdSubject.getId());
        assertEquals("Java", createdSubject.getName());
    }

    @Test
    public void testUpdateSubject() {
        // Given
        Long subjectId = 1L;
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setName("Updated Java");
    
        Subject updatedSubject = new Subject();
        updatedSubject.setId(subjectId);
        updatedSubject.setName("Updated Java");
    
        // Define the behavior of the mock SubjectRepository
        when(subjectRepository.save(any(Subject.class))).thenReturn(updatedSubject);
    
        // When
        Subject result = subjectService.updateSubject(subjectId, subjectDTO);
    
        // Then
        assertNotNull(result);
        assertEquals(subjectId, result.getId());
        assertEquals("Updated Java", result.getName());
    }

    // Test de la méthode getAllSubjects du service
//    @Test
//    public void testGetAllSubjects() {
//        // Créer une liste de sujets de test
//        List<Subject> subjects = new ArrayList<>();
//        subjects.add(new Subject("Java"));
//        subjects.add(new Subject("JavaScript"));
//
//        // Définir le comportement du mock SubjectRepository
//        when(subjectRepository.findAll()).thenReturn(subjects);
//
//        // Appeler la méthode getAllSubjects du service
//        List<SubjectDTO> retrievedSubjects = subjectService.getAllSubjects();
//
//        // Vérifier que la liste de sujets est renvoyée avec succès
//        assertNotNull(retrievedSubjects);
//        assertEquals(2, retrievedSubjects.size());
//        assertEquals("Java", retrievedSubjects.get(0).getName());
//        assertEquals("JavaScript", retrievedSubjects.get(1).getName());
//    }

    // Test de la méthode convertToDTO du service
    @Test
    public void testConvertToDTO() {
        // Créer un sujet de test
        Subject subject = new Subject("Java");

        // Appeler la méthode convertToDTO du service
        SubjectDTO subjectDTO = subjectService.toDTO(subject);

        // Vérifier que le DTO a été créé correctement
        assertNotNull(subjectDTO);
        assertEquals("Java", subjectDTO.getName());
    }

    // Test de la méthode getSubjectById du service
    @Test
    public void testGetSubjectById_ExistingSubject_ReturnsSubjectDTO() {
        // Given
        Long subjectId = 1L;
        Subject subject = new Subject();
        subject.setId(subjectId);
        subject.setName("Java");

        // Define the behavior of the mock subjectRepository
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(subject));

        // When
        SubjectDTO subjectDTO = subjectService.getSubjectById(subjectId);

        // Then
        assertEquals(subjectId, subjectDTO.getId());
        assertEquals("Java", subjectDTO.getName());
    }

    // Test de la méthode getSubjectById du service
    @Test
    public void testGetSubjectById_NonExistingSubject_ReturnsNull() {
        // Given
        Long subjectId = 1L;

        // Define the behavior of the mock subjectRepository
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.empty());

        // When
        SubjectDTO subjectDTO = subjectService.getSubjectById(subjectId);

        // Then
        assertNull(subjectDTO);
    }

    // Test de la méthode deleteSubject du service
    @Test
    public void testdeleteSubjectById() {
        // Given
        Long subjectId = 1L;

        // When
        subjectService.deleteSubjectById(subjectId);

        // Then
        verify(subjectRepository, times(1)).deleteById(subjectId);
    }
}

