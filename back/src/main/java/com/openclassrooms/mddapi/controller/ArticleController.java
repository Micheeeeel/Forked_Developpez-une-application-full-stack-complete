package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.dto.ArticleWithCommentsDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.exception.*;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.service.ArticleService;
import com.openclassrooms.mddapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    private UserService userService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<ArticleDTO> subscribedArticles() {

        // Récupérer l'utilisateur actuellement connecté
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserDTO userDTO = userService.getUserByName(username);

        List<ArticleDTO> subscribedArticles =  articleService.getSubscribedArticlesForUser(userDTO.getId());

        return subscribedArticles;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleWithCommentsDTO> getArticleById(@PathVariable Long id) {
        ArticleWithCommentsDTO articleWithCommentsDTO = articleService.getArticleById(id);
        if (articleWithCommentsDTO == null) {
            throw new ArticleNotFoundException("Article with ID " + id + " not found");
        }
        return ResponseEntity.ok(articleWithCommentsDTO);
    }

    @PostMapping
    public ResponseEntity<String> createArticle(@RequestBody ArticleDTO articleDTO) {
        if (articleDTO.getContent() == null || articleDTO.getContent().trim().isEmpty()) {
            throw new InvalidArticleDataException("Invalid article data");
        }

        Article createdArticle = articleService.createArticle(articleDTO);
        if (createdArticle != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("New Article created");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create Article");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateArticle(@PathVariable Long id, @RequestBody ArticleDTO articleDTO) {
        if (articleDTO.getContent() == null || articleDTO.getContent().trim().isEmpty()) {
            throw new InvalidArticleDataException("Invalid article data");
        }

        ArticleWithCommentsDTO existingArticle = articleService.getArticleById(id);
        if (existingArticle == null) {
            throw new ArticleNotFoundException("Article with ID " + id + " not found"); // 404: not found
        }

        Article updatedArticle = articleService.updateArticle(id, articleDTO);
        if (updatedArticle != null) {
            return ResponseEntity.ok().body("Article updated");
        } else {
            throw new UpdateArticleException("Failed to update Article");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticleById(@PathVariable Long id) {
        ArticleWithCommentsDTO articleWithCommentsDTO = articleService.getArticleById(id);
        if (articleWithCommentsDTO == null) {
            throw new ArticleNotFoundException("Article with ID " + id + " not found"); // 404: not found
        }

        try {
            this.articleService.deleteArticleById(id);
            return ResponseEntity.ok().body("{\"message\": \"Article deleted successfully\"}");
        } catch (Exception e) {
            throw new DeleteArticleException("Failed to delete Article with ID " + id);
        }
    }
}
