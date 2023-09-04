package com.openclassrooms.mddapi.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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

    @Lob    // permet de stocker de longs textes
    @Column(nullable = false, length = 5000) // adjust length as needed
    private String description;

    // Override toString() method for better debugging
    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static Subject createNewSubject(String name) {
        Subject subject = new Subject();
        subject.setName(name);
        return subject;    
    }  
}
