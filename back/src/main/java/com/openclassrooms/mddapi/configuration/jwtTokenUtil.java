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

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;  // 5 heures


    // vérifie la validité du token avec les détails de l'utilisateur et l'expiration du token
    public Boolean validateToken(String token, CustomUserDetails userDetails, String userLoginFromToken) {
        String email = userDetails.getEmail();
        return (userLoginFromToken.equals(email) && !isTokenExpired(token));    // si le login est bon et que le token n'est pas expiré
    }

    // vérifie si le token à expiré
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // renvoie la date d'expiration
    public Date getExpirationDateFromToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getExpiresAt();
    }

    //generate token for user
    public String generateToken(CustomUserDetails userDetails) {
        Algorithm algorithm = Algorithm.HMAC512(jwtSecret);     // algo de hachage sur 512 bit
        JWTCreator.Builder builder = JWT.create()
                .withSubject(userDetails.getEmail())    // ajoute l'email dans le token
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000));   // ajoute le délai d'expiration

        return builder.sign(algorithm);
    }

    //  Vérifie la conformité d'un jeton JWT en utilisant l'algorithme HMAC512 et la clé secrète.
    public DecodedJWT verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC512(jwtSecret);
        JWTVerifier verifier = JWT.require(algorithm).build();	   // Création d'un vérificateur JWT en utilisant l'algorithme défini.

        // Si la vérification réussit, un objet DecodedJWT contenant les informations décodées du jeton est retourné.
        return verifier.verify(token);
    }
}
