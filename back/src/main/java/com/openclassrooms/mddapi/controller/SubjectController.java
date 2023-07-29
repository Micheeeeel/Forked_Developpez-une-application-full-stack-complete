package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.SubjectDTO;
import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {
    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public List<SubjectDTO> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @PostMapping
    public ResponseEntity<String> createSubject(@RequestBody SubjectDTO subjectDTO) {
        if (subjectDTO.getName() == null || subjectDTO.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid Subject data");
        }

        Subject createdSubject = subjectService.createSubject(subjectDTO);
        if (createdSubject != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("New Subject created");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create Subject");
        }
    }
}
