package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User user);

    Optional<User> findByUsername(String testuser);

    Optional<User> findByEmail(String s);

    // Ajoutez ici des méthodes spécifiques au besoin, Spring Data JPA générera les requêtes automatiquement
}
