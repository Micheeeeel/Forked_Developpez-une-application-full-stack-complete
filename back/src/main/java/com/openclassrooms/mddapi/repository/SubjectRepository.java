package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    
    public Subject save(Subject subject) ;

    public void delete(Subject subject) ;

    public Optional<Subject> findById(Long id);

    Optional<Subject> findByName(String name);

    
}
