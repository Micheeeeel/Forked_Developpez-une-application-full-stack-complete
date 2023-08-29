package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.exception.UpdateSubjectException;
import com.openclassrooms.mddapi.exception.UpdateUserException;
import com.openclassrooms.mddapi.exception.UserNotFoundException;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUserDTO = userService.createUser(userDTO.getEmail(), userDTO.getUsername(), userDTO.getPassword());

        if (createdUserDTO != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("New User created");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create User");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        if (userDTO == null) {
            throw new UserNotFoundException("User not found with id: " + id); // Exception personnalisée
        } else {
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // Supposons que vous ayez une méthode pour obtenir les détails de l'utilisateur par nom d'utilisateur
        UserDTO userDTO = userService.getUserByName(username);
        if (userDTO == null) {
            throw new UserNotFoundException("User not found with userName: " + username); // Exception personnalisée
        } else {
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }
    }
}
