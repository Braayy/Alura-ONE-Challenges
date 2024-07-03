package io.github.braayy.forum.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.github.braayy.forum.features.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.issuer}")
    private String issuer;

    public Optional<String> generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(this.secret);

            return Optional.of(
                JWT.create()
                    .withIssuer(this.issuer)
                    .withSubject(user.getEmail())
                    .withExpiresAt(Instant.now().plus(2, ChronoUnit.HOURS))
                    .sign(algorithm)
            );
        } catch (JWTCreationException exception) {
            return Optional.empty();
        }
    }

    public Optional<String> getSubjectFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(this.secret);

            return Optional.of(
                JWT.require(algorithm)
                    .withIssuer(this.issuer)
                    .build()
                    .verify(token)
                    .getSubject()
            );
        } catch (JWTVerificationException exception) {
            return Optional.empty();
        }
    }

}
