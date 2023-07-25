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

import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import java.util.Optional;

@ExtendWith(SpringExtension.class)      // permet d'utiliser les extensions Spring dont @DataJpaTest
@DataJpaTest 
public class ArticleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ArticleRepository articleRepository;


    @Test
    public void testSaveArticle() {
        // Créer un nouvel utilisateur
        User author = User.createNewUser("test@example.com", "testuser", "password");
        entityManager.persist(author);

        // Créer un nouveau sujet
        Subject subject = Subject.createNewSubject("Java");
        entityManager.persist(subject);

        // Créer un nouvel article
        Article article = Article.createNewArticle("Article de test", "Contenu de l'article", subject, author);
        entityManager.persist(article);

        // Enregistrer l'article dans la base de données
        Article savedArticle = articleRepository.save(article);

        // Vérifier que l'article a été enregistré avec succès
        assertNotNull(savedArticle.getId());
        assertEquals("Article de test", savedArticle.getTitle());
        assertEquals("Contenu de l'article", savedArticle.getContent());
        assertEquals(subject.getId(), savedArticle.getSubject().getId());
        assertEquals(author.getId(), savedArticle.getAuthor().getId());
    }

    @Test
    public void testFindById() {
        // Créer un nouvel utilisateur
        User author = User.createNewUser("test@example.com", "testuser", "password");
        entityManager.persist(author);

        // Créer un nouveau sujet
        Subject subject = Subject.createNewSubject("Java");
        entityManager.persist(subject);

        // Créer un nouvel article
        Article article = Article.createNewArticle("Article de test", "Contenu de l'article", subject, author);
        entityManager.persist(article);

        // Récupérer l'ID de l'article créé
        Long articleId = article.getId();

        // Rechercher l'article par ID
        Article foundArticle = articleRepository.findById(articleId).orElse(null);

        // Vérifier que l'article récupéré correspond à l'article créé
        assertNotNull(foundArticle);
        assertEquals("Article de test", foundArticle.getTitle());
    }

    
    @Test
    public void testFindByTitle() {
        // Créer un nouvel utilisateur
        User author = User.createNewUser("test@example.com", "testuser", "password");
        entityManager.persist(author);

        // Créer un nouveau sujet
        Subject subject = Subject.createNewSubject("Java");
        entityManager.persist(subject);

        // Créer un nouvel article
        Article article = Article.createNewArticle("Article de test", "Contenu de l'article", subject, author);
        entityManager.persist(article);

        // Rechercher l'article par titre
        Optional<Article> foundArticle = articleRepository.findByTitle("Article de test");

        // Vérifier que l'article a été trouvé
        assertTrue(foundArticle.isPresent());
        foundArticle.ifPresent(a -> {
            assertEquals("Contenu de l'article", a.getContent());
            assertEquals(subject.getId(), a.getSubject().getId());
            assertEquals(author.getId(), a.getAuthor().getId());
        });
    }

    @Test
    public void testUpdateArticle() {
        // Créer un nouvel utilisateur
        User author = User.createNewUser("test@example.com", "testuser", "password");

        // Créer un nouveau sujet
        Subject subject = Subject.createNewSubject("Java");

        // Créer un nouvel article
        Article article = Article.createNewArticle("Article de test", "Contenu de l'article", subject, author);
        entityManager.persist(article);

       // Modifier les informations de l'article
       article.setTitle("Article mis à jour");
       article.setContent("Nouveau contenu de l'article");

       // Mettre à jour l'article dans la base de données
       Article updatedArticle = articleRepository.save(article);

       // Vérifier que les informations de l'article ont été mises à jour correctement
       assertEquals("Article mis à jour", updatedArticle.getTitle());
       assertEquals("Nouveau contenu de l'article", updatedArticle.getContent());
   }

   @Test
   public void testDeleteArticle() {
        // Créer un nouvel utilisateur
        User author = User.createNewUser("test@example.com", "testuser", "password");

        // Créer un nouveau sujet
        Subject subject = Subject.createNewSubject("Java");

        // Créer un nouvel article
        Article article = Article.createNewArticle("Article de test", "Contenu de l'article", subject, author);
        entityManager.persist(article);

       // Supprimer l'article de la base de données
       articleRepository.delete(article);

       // Vérifier que l'article a été correctement supprimé
       assertNull(entityManager.find(Article.class, article.getId()));
   }

   @Test
   public void testUniqueConstraints() {
       // Créer un nouvel utilisateur
        // Créer un nouvel utilisateur
        User author = User.createNewUser("test@example.com", "testuser", "password");

        // Créer un nouveau sujet
        Subject subject = Subject.createNewSubject("Java");

       // Créer 2 articles avec le même titre
       Article article1 = Article.createNewArticle("Article de test", "Contenu de l'article 1", subject, author);
       Article article2 = Article.createNewArticle("Article de test", "Contenu de l'article 2", subject, author);

       // Enregistrer le premier article dans la base de données
       articleRepository.save(article1);

       // Tenter d'enregistrer le deuxième article avec le même titre
       assertThrows(DataIntegrityViolationException.class, () -> articleRepository.save(article2));
   }

}
