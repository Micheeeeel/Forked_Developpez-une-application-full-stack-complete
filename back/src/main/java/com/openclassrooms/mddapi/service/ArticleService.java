package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.dto.ArticleWithCommentsDTO;
import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.exception.SubjectNotFoundException;
import com.openclassrooms.mddapi.exception.UserNotFoundException;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final CommentRepository commentRepository;
    private final CommentService commentService;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository, SubjectRepository subjectRepository, CommentRepository commentRepository, CommentService commentService) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.commentRepository = commentRepository;
        this.commentService = commentService;
    }

    public List<ArticleDTO> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ArticleWithCommentsDTO getArticleById(Long id) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        return optionalArticle.map(this::toWithCommentsDTO).orElse(null);
    }

    public Article createArticle(ArticleDTO articleDTO) {
        Article article = getArticle(articleDTO);

        // Save the article in the database
        return articleRepository.save(article);
    }

    public Article updateArticle(Long id, ArticleDTO articleDTO) {
        Article article = getArticle(articleDTO);

        article.setId(id);

        // Save the article in the database
        return articleRepository.save(article);
    }

    private Article getArticle(ArticleDTO articleDTO) {
        // Trouver l'auteur par userId
        User author = userRepository.findById(articleDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User with ID " + articleDTO.getUserId() + " not found"));

        // Trouver le sujet par subjectId
        Subject subject = subjectRepository.findById(articleDTO.getSubjectId())
                .orElseThrow(() -> new SubjectNotFoundException("Subject with ID " + articleDTO.getSubjectId() + " not found"));

        // Créer l'entité Article en utilisant la méthode statique de votre classe
        Article article = Article.createNewArticle(
                articleDTO.getTitle(),
                articleDTO.getContent(),
                subject,
                author
        );
        return article;
    }

    public void deleteArticleById(Long id) {
        articleRepository.deleteById(id);
    }



    private ArticleWithCommentsDTO toWithCommentsDTO(Article article) {
        List<CommentDTO> comments = getCommentsByArticleId(article.getId());

        return new ArticleWithCommentsDTO(article.getId(), article.getAuthor().getId(), article.getSubject().getId(),
                article.getTitle(), article.getContent(), article.getPublishedAt(), comments);
    }

    private ArticleDTO toDTO(Article article) {
        User author = article.getAuthor();
        Subject subject = article.getSubject();
        return new ArticleDTO(article.getId(), author.getId(),  author.getUsername(), subject.getId(), subject.getName(), article.getTitle(), article.getContent(), article.getPublishedAt() );
    }

    public List<CommentDTO> getCommentsByArticleId(Long articleId) {
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        return comments.stream().map(commentService:: toDTO).collect(Collectors.toList());
    }

}
