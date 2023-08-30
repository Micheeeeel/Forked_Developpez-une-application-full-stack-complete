package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    public CustomUserDetails loadUserByLogin(String login) throws UsernameNotFoundException {
        // on veut que l'utilisateur puisse se connecter soit avec son email, soit avec son userName
        // on essaye d'abord avec l'email
        Optional<User> userByEmail = userRepository.findByEmail(login);
        if (userByEmail.isPresent()) {
            return new CustomUserDetails(userByEmail.get());
        }

        // puis, si encore là, on essaye avec le userName
        Optional<User> userByUsername = userRepository.findByUsername(login);
        if (userByUsername.isPresent()) {
            return new CustomUserDetails(userByUsername.get());
        }

        throw new UsernameNotFoundException("User not found with login: " + login);
    }


    // méthode utilisé par l'authenticationManager
    @Override
    public CustomUserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return loadUserByLogin(userName);
    }

    public User save(User user) {
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


}
