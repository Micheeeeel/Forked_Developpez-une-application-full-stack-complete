package com.openclassrooms.mddapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "articles", uniqueConstraints = {
    @UniqueConstraint(columnNames = "title")
})
@Data // Génère les getters et setters.
@NoArgsConstructor  // Génère un constructeur sans paramètre.
@AllArgsConstructor // Génère un constructeur avec un paramètre pour chaque propriété de la classe.
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Subject subject;

    @ManyToOne
    private User author;

    @Column(nullable = false)    // le nom du titre ne peut pas être vide
    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Lob    // permet de stocker de longs textes
    @Column(nullable = false)    // le contenu de l'article ne peut pas être vide
    private String content;

    public static Article createNewArticle(String title, String content, Subject subject, User author) {
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setSubject(subject);
        article.setAuthor(author);
        return article;
    }


}
