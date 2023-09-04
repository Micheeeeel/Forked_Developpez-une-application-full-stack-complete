package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.model.Subject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByUserAndSubject(User user, Subject subject);

    boolean existsByUserIdAndSubjectId(Long userId, Long subjectId);

    Optional<Subscription> findByUserIdAndSubjectId(Long userId, Long subjectId);

    List<Subscription> findByUserId(Long userId);
}
