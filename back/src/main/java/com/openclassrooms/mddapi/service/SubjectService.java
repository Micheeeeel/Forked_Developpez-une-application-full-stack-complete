package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.SubjectDTO;
import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository, SubscriptionRepository subscriptionRepository) {

        this.subjectRepository = subjectRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<SubjectDTO> getAllSubjects(Long userId) {
        List<Subject> subjects = subjectRepository.findAll();
        return subjects.stream().map(subject -> {
            SubjectDTO dto = this.toDTO(subject);
            dto.setFollowed(subscriptionRepository.existsByUserIdAndSubjectId(userId, subject.getId()));
            return dto;
        }).collect(Collectors.toList());
    }

    public SubjectDTO getSubjectById(Long id) {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        return optionalSubject.map(this::toDTO).orElse(null);
    }

    public SubjectDTO toDTO(Subject subject) {
        return new SubjectDTO(subject.getId(), subject.getName(), subject.getDescription());
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

    public Subject updateSubject(Long id, SubjectDTO subjectDTO) {
        // Convertir le SubjectDTO en entité Subject
        Subject subject = new Subject(subjectDTO.getName());
        subject.setId(id);

        // Enregistrer le sujet dans la base de données
        return subjectRepository.save(subject);
    }

    public boolean subjectExists(String name) {
        // Vérifiez si un sujet avec ce nom existe déjà dans la base de données
        return subjectRepository.findByName(name).isPresent();
    }

}
