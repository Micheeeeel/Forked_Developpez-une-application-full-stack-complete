package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // encapsule les détails de l'utilisateur chargés à partir de son adresse e-mail
    public CustomUserDetails loadUserByUserEmail(String userEmail) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User not found with user email: " + userEmail));;	// authentication is done by email

        return new CustomUserDetails(user);

    }
}
