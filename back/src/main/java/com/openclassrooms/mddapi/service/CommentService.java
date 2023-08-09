package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.exception.CommentNotFoundException;
import com.openclassrooms.mddapi.exception.UserNotFoundException;
import com.openclassrooms.mddapi.exception.ArticleNotFoundException;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    public List<CommentDTO> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public CommentDTO getCommentById(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        return optionalComment.map(this::toDTO).orElse(null);
    }

    public Comment createComment(CommentDTO commentDTO) {
        Comment comment = getComment(commentDTO);

        // Save the comment in the database
        return commentRepository.save(comment);
    }

    public Comment updateComment(Long id, CommentDTO commentDTO) {
        Comment comment = getComment(commentDTO);

        comment.setId(id);

        // Save the comment in the database
        return commentRepository.save(comment);
    }

    private Comment getComment(CommentDTO commentDTO) {
        // Find the author by userId
        User author = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User with ID " + commentDTO.getUserId() + " not found"));

        // Find the article by articleId
        Article article = articleRepository.findById(commentDTO.getArticleId())
                .orElseThrow(() -> new ArticleNotFoundException("Article with ID " + commentDTO.getArticleId() + " not found"));

        // Create the Comment entity using your class method
        return Comment.createNewComment(
                commentDTO.getContent(),
                article,
                author
        );
    }

    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }

    public CommentDTO toDTO(Comment comment) {
        User author = comment.getAuthor();
        return new CommentDTO(author.getId(), author.getUsername(), comment.getArticle().getId(), comment.getContent(), comment.getCreatedAt());
    }
}
