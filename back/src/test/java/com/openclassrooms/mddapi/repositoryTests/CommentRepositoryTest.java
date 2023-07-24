package com.openclassrooms.mddapi.repositoryTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.CommentRepository;
import java.util.Date;
import java.util.Optional;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.openclassrooms.mddapi.repository.SubjectRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void testSaveComment() {
        // Créer un nouvel utilisateur
        User author = User.createNewUser("test@example.com", "testuser", "password");
        entityManager.persist(author);

        // Créer un nouveau sujet
        Subject subject = Subject.createNewSubject("Java");
        entityManager.persist(subject);

        // Créer un nouvel article
        Article article = Article.createNewArticle("Article de test", "Contenu de l'article", subject, author);
        entityManager.persist(article);

        // Créer un nouveau commentaire
        Comment comment = Comment.createNewComment("Commentaire de test", article, author);
        entityManager.persist(comment);

        // Enregistrer le commentaire dans la base de données
        Comment savedComment = commentRepository.save(comment);

        // Vérifier que le commentaire a été enregistré avec succès
        assertNotNull(savedComment.getId());
        assertEquals("Commentaire de test", savedComment.getContent());
        assertEquals(article.getId(), savedComment.getArticle().getId());
        assertEquals(author.getId(), savedComment.getAuthor().getId());
    }

    @Test
    public void testFindByContent() {
        // Créer un nouvel utilisateur
        User author = User.createNewUser("test@example.com", "testuser", "password");
        entityManager.persist(author);

        // Créer un nouveau sujet
        Subject subject = Subject.createNewSubject("Java");
        entityManager.persist(subject);

        // Créer un nouvel article
        Article article = Article.createNewArticle("Article de test", "Contenu de l'article", subject, author);
        entityManager.persist(article);

        // Créer un nouveau commentaire
        Comment comment = Comment.createNewComment("Commentaire de test", article, author);
        entityManager.persist(comment);

        // Rechercher le commentaire par contenu
        Optional<Comment> foundComment = commentRepository.findByContent("Commentaire de test");

        // Vérifier que le commentaire récupéré correspond au commentaire créé
        assertTrue(foundComment.isPresent()); // Vérifie si l'Optional contient une valeur non nulle
        foundComment.ifPresent(s -> assertEquals("Commentaire de test", s.getContent())); // Vérifie que le commentaire récupéré a le bon contenu
    }

    @Test
    public void testUpdateComment() {
        // Créer un nouvel utilisateur
        User author = User.createNewUser("test@example.com", "testuser", "password");
        entityManager.persist(author);

        // Créer un nouveau sujet
        Subject subject = Subject.createNewSubject("Java");
        entityManager.persist(subject);

        // Créer un nouvel article
        Article article = Article.createNewArticle("Article de test", "Contenu de l'article", subject, author);
        entityManager.persist(article);

        // Créer un nouveau commentaire
        Comment comment = Comment.createNewComment("Commentaire de test", article, author);
        entityManager.persist(comment);

        // Modifier le contenu du commentaire
        comment.setContent("Nouveau commentaire de test");

        // Mettre à jour le commentaire dans la base de données
        Comment updatedComment = commentRepository.save(comment);

        // Vérifier que le contenu du commentaire a été mis à jour correctement
        assertEquals("Nouveau commentaire de test", updatedComment.getContent());
    }

    @Test
    public void testDeleteComment() {
        // Créer un nouvel utilisateur
        User author = User.createNewUser("test@example.com", "testuser", "password");
        entityManager.persist(author);

        // Créer un nouveau sujet
        Subject subject = Subject.createNewSubject("Java");
        entityManager.persist(subject);

        // Créer un nouvel article
        Article article = Article.createNewArticle("Article de test", "Contenu de l'article", subject, author);
        entityManager.persist(article);

        // Créer un nouveau commentaire
        Comment comment = Comment.createNewComment("Commentaire de test", article, author);
        entityManager.persist(comment);

        // Supprimer le commentaire de la base de données
        commentRepository.delete(comment);

        // Vérifier que le commentaire a été correctement supprimé
        assertNull(entityManager.find(Comment.class, comment.getId()));
    }
}
