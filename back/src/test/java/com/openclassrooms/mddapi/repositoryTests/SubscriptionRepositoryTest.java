package com.openclassrooms.mddapi.repositoryTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class SubscriptionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Test
    public void testSaveSubscription() {
        // Créer un nouvel utilisateur
        User user = User.createNewUser("test@example.com", "testuser", "password");
        entityManager.persist(user);

        // Créer un nouveau sujet
        Subject subject = Subject.createNewSubject("Java");
        entityManager.persist(subject);

        // Créer une nouvelle souscription
        Subscription subscription = Subscription.createNewSubscription(user, subject);
        entityManager.persist(subscription);

        // Enregistrer la souscription dans la base de données
        Subscription savedSubscription = subscriptionRepository.save(subscription);

        // Vérifier que la souscription a été enregistrée avec succès
        assertNotNull(savedSubscription.getId());
        assertEquals(user.getId(), savedSubscription.getUser().getId());
        assertEquals(subject.getId(), savedSubscription.getSubject().getId());
    }

    @Test
    public void testFindByUserAndSubject() {
        // Créer un nouvel utilisateur
        User user = User.createNewUser("test@example.com", "testuser", "password");
        entityManager.persist(user);

        // Créer un nouveau sujet
        Subject subject = Subject.createNewSubject("Java");
        entityManager.persist(subject);

        // Créer une nouvelle souscription
        Subscription subscription = Subscription.createNewSubscription(user, subject);
        entityManager.persist(subscription);

        // Rechercher la souscription par utilisateur et sujet
        Optional<Subscription> foundSubscription = subscriptionRepository.findByUserAndSubject(user, subject);

        // Vérifier que la souscription a été trouvée
        assertTrue(foundSubscription.isPresent());
        foundSubscription.ifPresent(s -> {
            assertEquals(user.getId(), s.getUser().getId());
            assertEquals(subject.getId(), s.getSubject().getId());
        });
    }

    @Test
    public void testUniqueConstraints() {
        // Créer un nouvel utilisateur
        User user = User.createNewUser("test@example.com", "testuser", "password");
        entityManager.persist(user);

        // Créer un nouveau sujet
        Subject subject = Subject.createNewSubject("Java");
        entityManager.persist(subject);

        // Créer 2 souscriptions pour le même utilisateur et sujet
        Subscription subscription1 = Subscription.createNewSubscription(user, subject);
        Subscription subscription2 = Subscription.createNewSubscription(user, subject);

        // Enregistrer la première souscription dans la base de données
        subscriptionRepository.save(subscription1);

        // Tenter d'enregistrer la deuxième souscription avec le même utilisateur et sujet
        assertThrows(DataIntegrityViolationException.class, () -> subscriptionRepository.save(subscription2));
    }

    @Test
    public void testDeleteSubscription() {
        // Créer un nouvel utilisateur
        User user = User.createNewUser("test@example.com", "testuser", "password");
        entityManager.persist(user);

        // Créer un nouveau sujet
        Subject subject = Subject.createNewSubject("Java");
        entityManager.persist(subject);

        // Créer une nouvelle souscription
        Subscription subscription = Subscription.createNewSubscription(user, subject);
        entityManager.persist(subscription);

        // Supprimer la souscription de la base de données
        subscriptionRepository.delete(subscription);

        // Vérifier que la souscription a été correctement supprimée
        assertNull(entityManager.find(Subscription.class, subscription.getId()));
    }
}
