package com.mua.ibbeauty.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtConfigurationProperties jwtConfigurationProperties;
    private final ResourceLoader resourceLoader;

    public String generateToken(String email) throws IOException {
        return createToken(email);
    }

    private String createToken(String email) throws IOException {
        Instant currentInstant = Instant.now();
        Date currentDate = Date.from(currentInstant);

        int expirationTimeInMinutes = jwtConfigurationProperties.getExpirationTimeInMinutes();
        Instant expirationInstant = currentInstant.plus(expirationTimeInMinutes, ChronoUnit.MINUTES);
        Date tokenExpirationDate = Date.from(expirationInstant);

        return Jwts.builder()
                .subject(email)
                .issuedAt(currentDate)
                .expiration(tokenExpirationDate)
                .signWith(getSignKey())
                .compact();
    }

    public String extractUserName(String token) throws IOException {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean validateToken(String token, UserDetails userDetails) throws IOException {
        final String userName = extractUserName(token);
        boolean emailMatchesUsername = userName.equals(userDetails.getUsername());
        return emailMatchesUsername && !isTokenExpired(token);
    }

    private Date extractExpiration(String token) throws IOException {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) throws IOException {
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getSignKey() throws IOException {
        String secretKey = jwtConfigurationProperties.getSecretKey();
        Resource resource = resourceLoader.getResource(secretKey);
        byte[] keyBytes = Files.readAllBytes(Paths.get(resource.getURI()));
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws IOException {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private boolean isTokenExpired(String token) throws IOException {
        Date tokenExpirationDate = extractExpiration(token);
        return tokenExpirationDate.before(new Date());
    }
}
