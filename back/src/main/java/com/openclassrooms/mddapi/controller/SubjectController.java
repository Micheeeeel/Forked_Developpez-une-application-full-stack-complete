package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.SubjectDTO;
import com.openclassrooms.mddapi.exception.DeleteSubjectException;
import com.openclassrooms.mddapi.exception.InvalidSubjectDataException;
import com.openclassrooms.mddapi.exception.SubjectAlreadyExistsException;
import com.openclassrooms.mddapi.exception.SubjectNotFoundException;
import com.openclassrooms.mddapi.exception.UpdateSubjectException;
import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDTO> getSubjectById(@PathVariable Long id) {
        SubjectDTO subjectDTO = subjectService.getSubjectById(id);
        if (subjectDTO == null) {
            throw new SubjectNotFoundException("Subject with ID " + id + " not found");
        }
        return ResponseEntity.ok(subjectDTO);
    }

    @PostMapping
    public ResponseEntity<String> createSubject(@RequestBody SubjectDTO subjectDTO) {
        if (subjectDTO.getName() == null || subjectDTO.getName().trim().isEmpty()) {
            throw new InvalidSubjectDataException("Invalid Subject data");
        }

        // Vous pouvez vérifier si le sujet existe déjà ici et lever une exception si c'est le cas
        if (subjectService.subjectExists(subjectDTO.getName())) {
            throw new SubjectAlreadyExistsException("Subject with name " + subjectDTO.getName() + " already exists");
        }

        Subject createdSubject = subjectService.createSubject(subjectDTO);
        if (createdSubject != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("New Subject created");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create Subject");
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateSubject(@PathVariable Long id, @RequestBody SubjectDTO subjectDTO) {
        if (subjectDTO.getName() == null || subjectDTO.getName().trim().isEmpty()) {
            throw new InvalidSubjectDataException("Invalid Subject data");
        }

        SubjectDTO existingSubject = subjectService.getSubjectById(id);
        if (existingSubject == null) {
            throw new SubjectNotFoundException("Subject with ID " + id + " not found"); // 404: not found
        }
    
        Subject updatedSubject = subjectService.updateSubject(id, subjectDTO);
        if (updatedSubject != null) {
            return ResponseEntity.ok().body("Subject updated");   // 200: ok
        } else {
            throw new UpdateSubjectException("Failed to update Subject");    // Custom exception for failure to update
        }
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubjectById(@PathVariable Long id) {
        SubjectDTO subjectDTO = subjectService.getSubjectById(id);
        if (subjectDTO == null) {
            throw new SubjectNotFoundException("Subject with ID " + id + " not found"); // 404: not found
        }
    
        try {
            this.subjectService.deleteSubjectById(id);
            return ResponseEntity.ok().body("{\"message\": \"Subject deleted successfully\"}");
        } catch (Exception e) {
            throw new DeleteSubjectException("Failed to delete Subject with ID " + id); // Custom exception for failure to delete
        }
    }

}
