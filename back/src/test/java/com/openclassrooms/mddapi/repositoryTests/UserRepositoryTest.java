package com.openclassrooms.mddapi.repositoryTests;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)      // permet d'utiliser les extensions Spring dont @DataJpaTest
@DataJpaTest    // configure un contexte de test léger avec seulement les composants nécessaires pour tester les fonctionnalités de persistance de la couche de données.
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        // Créer un nouvel utilisateur
        User user = new User();
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setPassword("password");

        // Enregistrer l'utilisateur dans la base de données
        User savedUser = userRepository.save(user);

        // Vérifier que l'utilisateur a été enregistré avec succès
        assertNotNull(savedUser.getId());
        assertEquals("test@example.com", savedUser.getEmail());
        assertEquals("testuser", savedUser.getUsername());
        assertEquals("password", savedUser.getPassword());
    }

    @Test
    public void testFindByUsername() {
        // Créer un nouvel utilisateur
        User user = new User();
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setPassword("password");
        entityManager.persist(user);

        // Rechercher l'utilisateur par nom d'utilisateur
        Optional<User>  foundUser = userRepository.findByUsername("testuser");

        // Vérifier que l'utilisateur a été trouvé
        assertTrue(foundUser.isPresent()); // Vérifie si l'Optional contient une valeur non nulle
        foundUser.ifPresent(u -> {
            assertEquals("test@example.com", u.getEmail());
            assertEquals("password", u.getPassword());
        });
    }

    @Test
    public void testFindByEmail() {
        // Créer plusieurs utilisateurs avec des adresses e-mail différentes
        User user1 = new User(1L, "user1@example.com", "user1", "password");
        User user2 = new User(2L, "user2@example.com", "user2", "password");
        User user3 = new User(3L, "user3@example.com", "user3", "password");

        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.persist(user3);

        // Rechercher les utilisateurs par adresse e-mail
        Optional<User> foundUser = userRepository.findByEmail("user2@example.com");

        // Vérifier que l'utilisateur a été trouvé
        assertTrue(foundUser.isPresent()); // Vérifie si l'Optional contient une valeur non nulle
        foundUser.ifPresent(u -> {
            assertEquals("user2", u.getUsername());
            assertEquals("password", u.getPassword());
        });
    }
}
