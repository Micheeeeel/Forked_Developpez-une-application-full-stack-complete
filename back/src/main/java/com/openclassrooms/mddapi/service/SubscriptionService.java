package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Subscription;
import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.SubscriptionRepository;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository, UserRepository userRepository, SubjectRepository subjectRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
    }

    public boolean isSubscribed(Long subjectId, Long userId) {
        return subscriptionRepository.existsByUserIdAndSubjectId(userId, subjectId);
    }

    public void subscribeToSubject(Long subjectId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(); // Vous pouvez gérer les exceptions ici
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(); // Vous pouvez gérer les exceptions ici
        Subscription subscription = Subscription.createNewSubscription(user, subject);
        subscriptionRepository.save(subscription);
    }

    public void unsubscribeSubject(Long subjectId, Long userId) {
        Subscription subscription = subscriptionRepository.findByUserIdAndSubjectId(userId, subjectId).orElseThrow(); // Vous pouvez gérer les exceptions ici
        subscriptionRepository.delete(subscription);
    }
}
