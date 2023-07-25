package com.openclassrooms.mddapi.repositoryTests;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)      // permet d'utiliser les extensions Spring dont @DataJpaTest
@DataJpaTest    // configure un contexte de test léger avec seulement les composants nécessaires pour tester les fonctionnalités sur une bd H2.
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;



    @Test
    public void testSaveUser() {
        // Créer un nouvel utilisateur
        User user = User.createNewUser("test@example.com","testuser","password");
        
        // Enregistrer l'utilisateur dans la base de données
        User savedUser = userRepository.save(user);

        // Vérifier que l'utilisateur a été enregistré avec succès
        assertNotNull(savedUser.getId());
        assertEquals("test@example.com", savedUser.getEmail());
        assertEquals("testuser", savedUser.getUsername());
        assertEquals("password", savedUser.getPassword());
    }
    @Test
    public void testFindById() {
        // Créer un nouvel utilisateur
        User user = User.createNewUser("test@example.com","testuser","password");
        entityManager.persist(user);

        // Récupérer l'ID de l'utilisateur créé
        Long userId = user.getId();

        // Rechercher l'utilisateur par ID
        User foundUser = userRepository.findById(userId).orElse(null);

        // Vérifier que l'utilisateur récupéré correspond à l'utilisateur créé
        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUsername());
    }

    @Test
    public void testFindByUsername() {
        // Créer un nouvel utilisateur
        User user = User.createNewUser("test@example.com","testuser","password");
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
        // Créer un nouvel utilisateur
        User user1 = User.createNewUser("user1@example.com", "user1", "password1");
        entityManager.persist(user1);
        User user2 = User.createNewUser("user2@example.com", "user2", "password2");
        entityManager.persist(user2);
        User user3 = User.createNewUser("user3@example.com", "user3", "password3");
        entityManager.persist(user3);

        // Rechercher l'utilisateur par email
        Optional<User> foundUser  = userRepository.findByEmail("user2@example.com");

        // Vérifier que l'utilisateur a été trouvé
        assertTrue(foundUser.isPresent()); // Vérifie si l'Optional contient une valeur non nulle
        foundUser.ifPresent(u -> {
            assertEquals("user2", u.getUsername());
            assertEquals("password2", u.getPassword());
        });

    }

    @Test
    public void testUpdateUser() {
        // Créer un nouvel utilisateur
        User user = User.createNewUser("test@example.com","testuser","password");
        entityManager.persist(user);

        // Modifier les informations de l'utilisateur
        user.setUsername("updateduser");
        user.setEmail("updated@example.com");

        // Mettre à jour l'utilisateur dans la base de données
        User updatedUser = userRepository.save(user);

        // Vérifier que les informations de l'utilisateur ont été mises à jour correctement
        assertEquals("updateduser", updatedUser.getUsername());
        assertEquals("updated@example.com", updatedUser.getEmail());
    }

    @Test
    public void testDeleteUser() {
        // Créer un nouvel utilisateur
        User user = User.createNewUser("test@example.com","testuser","password");
        entityManager.persist(user);

        // Supprimer l'utilisateur de la base de données
        userRepository.delete(user);

        // Vérifier que l'utilisateur a été correctement supprimé
        assertNull(entityManager.find(User.class, user.getId()));
    }

    @Test
    public void testUniqueConstraints() {
        // Créer un utilisateur avec un nom d'utilisateur et une adresse e-mail déjà utilisés
        User user1 = User.createNewUser("user1@example.com", "user1", "password1");
        User user2 = User.createNewUser("user1@example.com", "user2", "password2");

        // Enregistrer le premier utilisateur dans la base de données
        userRepository.save(user1);

        // Tenter d'enregistrer le deuxième utilisateur avec les mêmes identifiants
        assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(user2));
    }

    @Test
    public void testPagination() {
        // Insérer plusieurs utilisateurs dans la base de données de test
        for (int i = 1; i <= 10; i++) {
            User user = User.createNewUser("user" + i + "@example.com", "user" + i, "password"); 
            entityManager.persist(user);
        }

        // Récupérer une page d'utilisateurs (2 utilisateurs par page)
        PageRequest pageable = PageRequest.of(0, 2);
        Page<User> usersPage = userRepository.findAll(pageable);

        // Vérifier que la pagination fonctionne correctement et renvoie 2 utilisateurs par page
        assertEquals(2, usersPage.getContent().size());
        assertEquals(5, usersPage.getTotalPages());
    }
}
