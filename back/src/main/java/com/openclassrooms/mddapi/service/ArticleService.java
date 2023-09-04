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
import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
        private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository, SubjectRepository subjectRepository, CommentRepository commentRepository, CommentService commentService, SubscriptionRepository subscriptionRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.commentRepository = commentRepository;
        this.commentService = commentService;
        this.subscriptionRepository = subscriptionRepository;
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

    private ArticleDTO toDTO(Article article) {
        User author = article.getAuthor();
        Subject subject = article.getSubject();
        return new ArticleDTO(article.getId(), author.getId(),  author.getUsername(), subject.getId(), subject.getName(), article.getTitle(), article.getContent(), article.getPublishedAt() );
    }

    private ArticleWithCommentsDTO toWithCommentsDTO(Article article) {
        List<CommentDTO> comments = getCommentsByArticleId(article.getId());

        User author = article.getAuthor();
        Subject subject = article.getSubject();

        return new ArticleWithCommentsDTO(article.getId(), author.getId(), author.getUsername(), subject.getId(), subject.getName(),
                article.getTitle(), article.getContent(), article.getPublishedAt(), comments);
    }


    public List<CommentDTO> getCommentsByArticleId(Long articleId) {
        Sort sort = Sort.by(Sort.Order.desc("createdAt"));
        List<Comment> comments = commentRepository.findByArticleId(articleId, sort);
        return comments.stream().map(commentService:: toDTO).collect(Collectors.toList());
    }

    public List<ArticleDTO> getSubscribedArticlesForUser(Long userId) {
        List<Article> allArticles = articleRepository.findAll();

        // get all subjects that the user is subscribed to
        List<Subscription> subscriptions = subscriptionRepository.findByUserId(userId);

        // then get all articles that belong to those subjects
        List<Article> subscribedArticles = allArticles.stream()
                .filter(article -> subscriptions.stream().anyMatch(subscription -> subscription.getSubject().getId().equals(article.getSubject().getId())))
                .collect(Collectors.toList());

        return subscribedArticles.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
