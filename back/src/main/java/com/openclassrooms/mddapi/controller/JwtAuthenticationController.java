package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.model.JwtRequest;
import com.openclassrooms.mddapi.model.JwtResponse;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.CustomUserDetails;
import com.openclassrooms.mddapi.service.JwtUserDetailsService;
import com.openclassrooms.mddapi.service.UserService;
import com.openclassrooms.mddapi.configuration.jwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private jwtTokenUtil jwtTokenUtil;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public JwtAuthenticationController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {

        // Convert in User
        User user = User.createNewUser(userDTO.getEmail(), userDTO.getUsername(), userDTO.getPassword());

        // save it into the db by encrypting the password
        userDetailsService.save(user);

        // Load user details
        final CustomUserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        // génère un jeton JWT valide en cas de succès de l'authentification
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));

    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        // authentification
        authenticate(authenticationRequest.getLogin(), authenticationRequest.getPassword());

        // Génère un jeton JWT valide à partir des info utilisateur en cas de succès de l'authentification
        final CustomUserDetails userDetails = userDetailsService
                .loadUserByLogin(authenticationRequest.getLogin());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    // Si l'authentification réussit, la méthode authenticate() ne lèvera pas d'exception et l'exécution continuera.
    // Dans le cas contraire, une exception sera levée, indiquant que l'authentification a échoué.
    private void authenticate(String login, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }


    @PutMapping("/api/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO updatedUserDTO = userService.updateUser(id, userDTO.getEmail(), userDTO.getUsername());

        // Génère un jeton JWT valide à partir des info utilisateur
        final CustomUserDetails userDetails = userDetailsService.loadUserByLogin(updatedUserDTO.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));

    }
}
