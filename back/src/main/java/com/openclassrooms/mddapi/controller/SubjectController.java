package com.openclassrooms.mddapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.repository.SubjectRepository;

// javadoc comments
/**
 * The SubjectController class is the controller of the Subject entity.
 * It allows to get all the subjects from the database.
 * 
 * @see Subject
 * @see SubjectRepository
 * @see RestController
 * @see GetMapping
 * @see Autowired
 * @see List
 * @see SubjectRepository#findAll()
 * @see #getAllSubjects()
 */
@RestController
public class SubjectController {

    @Autowired 
    private final SubjectRepository subjectRepository;

    public SubjectController(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @GetMapping("/subjects")
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }
    
    
}