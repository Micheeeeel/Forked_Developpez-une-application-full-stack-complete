package com.openclassrooms.mddapi.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "subjects", uniqueConstraints = {
    @UniqueConstraint(columnNames = "name")
})
@Data // Génère les getters et setters.
@NoArgsConstructor  // Génère un constructeur sans paramètre.
@AllArgsConstructor // Génère un constructeur avec un paramètre pour chaque propriété de la classe.
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)    // le nom du sujt ne peut pas être vide
    private String name;

    public Subject(String name) {
        this.name = name;
    }

    // Override toString() method for better debugging
    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
