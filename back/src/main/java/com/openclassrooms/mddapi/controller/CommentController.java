package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.exception.CommentNotFoundException;
import com.openclassrooms.mddapi.exception.InvalidCommentDataException;
import com.openclassrooms.mddapi.exception.UpdateCommentException;
import com.openclassrooms.mddapi.exception.DeleteCommentException;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<CommentDTO> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        CommentDTO commentDTO = commentService.getCommentById(id);
        if (commentDTO == null) {
            throw new CommentNotFoundException("Comment with ID " + id + " not found");
        }
        return ResponseEntity.ok(commentDTO);
    }

    @PostMapping
    public ResponseEntity<String> createComment(@RequestBody CommentDTO commentDTO) {
        if (commentDTO.getContent() == null || commentDTO.getContent().trim().isEmpty()) {
            throw new InvalidCommentDataException("Invalid comment data");
        }

        Comment createdComment = commentService.createComment(commentDTO);
        if (createdComment != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("New Comment created");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create Comment");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
        if (commentDTO.getContent() == null || commentDTO.getContent().trim().isEmpty()) {
            throw new InvalidCommentDataException("Invalid comment data");
        }

        CommentDTO existingComment = commentService.getCommentById(id);
        if (existingComment == null) {
            throw new CommentNotFoundException("Comment with ID " + id + " not found"); // 404: not found
        }

        Comment updatedComment = commentService.updateComment(id, commentDTO);
        if (updatedComment != null) {
            return ResponseEntity.ok().body("Comment updated");
        } else {
            throw new UpdateCommentException("Failed to update Comment");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCommentById(@PathVariable Long id) {
        CommentDTO commentDTO = commentService.getCommentById(id);
        if (commentDTO == null) {
            throw new CommentNotFoundException("Comment with ID " + id + " not found"); // 404: not found
        }

        try {
            this.commentService.deleteCommentById(id);
            return ResponseEntity.ok().body("{\"message\": \"Comment deleted successfully\"}");
        } catch (Exception e) {
            throw new DeleteCommentException("Failed to delete Comment with ID " + id);
        }
    }
}