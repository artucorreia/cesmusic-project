package com.blog.cesmusic.services.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.blog.cesmusic.data.DTO.v1.auth.TokenDTO;
import com.blog.cesmusic.exceptions.auth.JwtCreationTokenException;
import com.blog.cesmusic.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class TokenService {
    private final Logger LOGGER = Logger.getLogger(TokenService.class.getName());
    private String subject;

    @Value("${security.jwt.token.secret}")
    private String secretKey;

    public TokenDTO generateToken(User user) {
        LOGGER.info("Generating token");
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            Instant createdAt = getIssueDate();
            Instant expiresIn = generateExpirationDate(createdAt);
            String token = JWT.create()
                    .withIssuer("cesmusic")
                    .withSubject(user.getId().toString())
                    .withIssuedAt(createdAt)
                    .withExpiresAt(expiresIn)
                    .sign(algorithm);

            return new TokenDTO(user.getId(), user.getRole(), token, createdAt, expiresIn);
        }
        catch (JWTCreationException e) {
            throw new JwtCreationTokenException("Error while generating a token");
        }
    }

    public UUID validateToken(String token) {
        LOGGER.info("Validating token");
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            subject = JWT.require(algorithm)
                    .withIssuer("cesmusic")
                    .build()
                    .verify(token)
                    .getSubject();
            return UUID.fromString(subject);
        }
        catch (JWTVerificationException e) {
            LOGGER.warning("Token validation failed: " + e.getMessage());
            return null;
        }
    }

    private Instant generateExpirationDate(Instant createdAt) {
        return createdAt.plus(Duration.ofHours(12));
    }

    private Instant getIssueDate() {
        return Instant.now();
    }

    public UUID getUserId() {
        return UUID.fromString(subject);
    }
}
