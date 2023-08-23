package com.openclassrooms.mddapi.configuration;

import java.io.Serializable;
import java.util.Date;

import com.openclassrooms.mddapi.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;


@Component
public class jwtTokenUtil implements Serializable {

    @Value("${jwt.secret}")
    private String jwtSecret;



    //  Vérifie la conformité d'un jeton JWT en utilisant l'algorithme HMAC512 et la clé secrète.
    public DecodedJWT verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC512(jwtSecret);
        JWTVerifier verifier = JWT.require(algorithm).build();	   // Création d'un vérificateur JWT en utilisant l'algorithme défini.

        // Si la vérification réussit, un objet DecodedJWT contenant les informations décodées du jeton est retourné.
        return verifier.verify(token);
    }

    // vérifie la validité du token avec les détails de l'utilisateur et l'expiration du token
    public Boolean validateToken(String token, CustomUserDetails userDetails, String userLoginFromToken) {
        String email = userDetails.getEmail();
        return (userLoginFromToken.equals(email) && !isTokenExpired(token));    // si le login est bon et que le token n'est pas expiré
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getExpiresAt();
    }
}
