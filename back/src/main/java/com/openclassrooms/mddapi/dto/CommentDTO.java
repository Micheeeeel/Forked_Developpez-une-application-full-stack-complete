package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Long userId;

    private String authorName; // Nom de l'auteur

    private Long articleId;

    @NotBlank
    @Size(max = 5000)
    private String content;

    private Date createdAt;

}
