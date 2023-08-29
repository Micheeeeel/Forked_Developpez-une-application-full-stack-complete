package com.openclassrooms.mddapi.configuration;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.openclassrooms.mddapi.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.openclassrooms.mddapi.service.JwtUserDetailsService;
@Component

public class jwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private jwtTokenUtil jwtTokenUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String userLoginFromToken = null;
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                // La conformité du jeton JWT est ensuite vérifié et ses données récupérées
                DecodedJWT jwt = jwtTokenUtil.verifyToken(token);
                userLoginFromToken = jwt.getSubject();

                // Once we get the token, we have to validate it with the user it refer to
                if(userLoginFromToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    // Chargement des détails de l'utilisateur
                    CustomUserDetails userDetails = this.jwtUserDetailsService.loadUserByLogin(userLoginFromToken);

                    // Validation finale du jeton et configuration de l'authentification
                    if(jwtTokenUtil.validateToken(token, userDetails, userLoginFromToken)){

                        // une instance UsernamePasswordAuthenticationToken est créée avec les détails de l'utilisateur, ses rôles et autorisations
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                        // Ajout des détails de l'authentification
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        // L'authentification est définie dans le contexte de sécurité : signifie que l'utilisateur actuel est considéré comme authentifié
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    }

                }

            } catch (Exception e) {
                System.out.println("Unable to get or validate JWT Token or JWT Token has expired");
            }
        } else{
            logger.warn("Header with Bearer not found");
        }

        filterChain.doFilter(request, response);
    }
}

