package com.openclassrooms.mddapi.IST;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.repository.SubjectRepository;

@AutoConfigureMockMvc
@SpringBootTest
public class SubjectIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SubjectRepository subjectRepository;

    // test de l'API REST pour récupérer l'ensemble des sujets
    @Test
    public void testGetAllSubjects() throws Exception {

        // Insérer des sujets de test dans la base de données
        Subject subject1 = new Subject("Java");
        Subject subject2 = new Subject("JavaScript");
        subjectRepository.save(subject1);
        subjectRepository.save(subject2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/subjects"))
                .andExpect(MockMvcResultMatchers.status().isOk())   // vérifie que le statut de la réponse est 200 OK, cela signifie que la requête a été traitée avec succès par le serveur
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Java"))   // vérifie que le nom du sujet créé par la réponse JSON est bien "Java"
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("JavaScript"));    // vérifie que le nom du sujet créé par la réponse JSON est bien "Java"
    }


    // @Test
    // public void testCreateSubject() throws Exception {
    //     String subjectName = "Java";

    //     // Envoie d'une requête HTTP POST pour créer un nouveau sujet
    //     mockMvc.perform(MockMvcRequestBuilders.post("/api/subjects")
    //             .contentType(MediaType.APPLICATION_JSON)    // Type de contenu de la requête
    //             .content("{\"name\":\"" + subjectName + "\"}"))  // Corps de la requête avec une propriété "name" contenant la valeur "Java"
    //             .andExpect(MockMvcResultMatchers.status().isOk())   // Vérifie que le statut de la réponse est 200 OK, cela signifie que la requête a été traitée avec succès par le serveur
    //             .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(subjectName));    // Vérifie que le nom du sujet créé par la réponse JSON est bien "Java"
    // }

    // @Test
    // public void testGetSubjectById() throws Exception {
    //     // Créer un sujet dans la base de données pour les besoins du test
    //     Subject subject = new Subject("Java");
    //     subjectRepository.save(subject);

    //     // Envoie d'une requête HTTP GET pour récupérer le sujet par ID
    //     mockMvc.perform(MockMvcRequestBuilders.get("/api/subjects/" + subject.getId()))
    //             .andExpect(MockMvcResultMatchers.status().isOk())
    //             .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(subject.getName()));
    // }

    // Autres tests pour les autres fonctionnalités du SubjectController, SubjectService et SubjectRepository...

}
