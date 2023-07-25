package com.openclassrooms.mddapi.repositoryTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import java.util.Optional;

@ExtendWith(SpringExtension.class)      // permet d'utiliser les extensions Spring dont @DataJpaTest
@DataJpaTest 
public class SubjectRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SubjectRepository subjectRepository;



    @Test
    public void testCreateSubject() {
        // Créer un nouveau sujet
        Subject subject = Subject.createNewSubject("Java");

        //Subject subject = new Subject("Java");

        // Enregistrer le sujet dans la base de données
        Subject savedSubject = subjectRepository.save(subject);

        // Vérifier que le sujet a été enregistré avec succès et qu'il possède un ID
        assertNotNull(savedSubject.getId());
        assertEquals("Java", savedSubject.getName());
    }

    @Test
    public void testFindById() {
        // Créer un nouveau sujet
        Subject subject = new Subject("Java");
        entityManager.persist(subject);

        // Récupérer l'ID du sujet créé
        Long subjectId = subject.getId();

        // Rechercher le sujet par ID
        Optional<Subject> foundSubject = subjectRepository.findById(subjectId);

        // Vérifier que le sujet récupéré correspond au sujet créé
        assertTrue(foundSubject.isPresent());
        foundSubject.ifPresent(s -> assertEquals("Java", s.getName()));

    }

    @Test
    public void testFindByName() {
        // Créer un nouveau sujet
        Subject subject = Subject.createNewSubject("Java");
        entityManager.persist(subject);

        // Rechercher le sujet par nom
        Optional<Subject> foundSubject = subjectRepository.findByName("Java");

        // Vérifier que le sujet récupéré correspond au nom spécifié
        assertTrue(foundSubject.isPresent());
        foundSubject.ifPresent(s -> assertEquals("Java", s.getName()));

    }

    @Test
    public void testUpdateSubject() {
        // Créer un nouveau sujet
        Subject subject = Subject.createNewSubject("Java");
        entityManager.persist(subject);

        // Modifier le nom du sujet
        subject.setName("JavaScript");

        // Mettre à jour le sujet dans la base de données
        Subject updatedSubject = subjectRepository.save(subject);

        // Vérifier que le sujet a été mis à jour correctement
        assertEquals("JavaScript", updatedSubject.getName());
    }

    @Test
    public void testDeleteSubject() {
        // Créer un nouveau sujet
        Subject subject = Subject.createNewSubject("Java");
        entityManager.persist(subject);

        // Supprimer le sujet de la base de données
        subjectRepository.delete(subject);

        // Vérifier que le sujet a été correctement supprimé
        assertNull(entityManager.find(Subject.class, subject.getId()));
    }

        @Test
    public void testUniqueConstraints() {
        // Créer un sujet avec un nom déjà utilisé
        Subject subject1 = Subject.createNewSubject("Java");
        Subject subject2 = Subject.createNewSubject("Java");

        // Enregistrer le premier sujet dans la base de données
        subjectRepository.save(subject1);

        // Tenter d'enregistrer le deuxième sujet avec le même nom
        assertThrows(DataIntegrityViolationException.class, () -> subjectRepository.save(subject2));
    }

        @Test
    public void testPagination() {
        // Insérer plusieurs sujets dans la base de données de test
        for (int i = 1; i <= 10; i++) {
            Subject subject = new Subject("Subject " + i);
            entityManager.persist(subject);
        }

        // Récupérer une page de sujets (2 sujets par page)
        PageRequest pageable = PageRequest.of(0, 2);
        Page<Subject> subjectsPage = subjectRepository.findAll(pageable);

        // Vérifier que la pagination fonctionne correctement et renvoie 2 sujets par page
        assertEquals(2, subjectsPage.getContent().size());
        assertEquals(5, subjectsPage.getTotalPages());
    }

}
