package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.SubjectDTO;
import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<SubjectDTO> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        return subjects.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public SubjectDTO getSubjectById(Long id) {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        return optionalSubject.map(this::convertToDTO).orElse(null);
    }

    public SubjectDTO convertToDTO(Subject subject) {
        return new SubjectDTO(subject.getId(), subject.getName());
    }

    public Subject createSubject(SubjectDTO subjectDTO) {
        // Convertir le SubjectDTO en entité Subject
        Subject subject = new Subject(subjectDTO.getName());

        // Enregistrer le sujet dans la base de données
        return subjectRepository.save(subject);
    }

    public void deleteSubjectById(Long id) {
        subjectRepository.deleteById(id);
    }

}
