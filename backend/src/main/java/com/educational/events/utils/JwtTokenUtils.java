package com.educational.events.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

@Component
public class JwtTokenUtils {

    private final SecretKey key;
    private final Duration lifetime;

    public JwtTokenUtils(@Value("${jwt.secret}") String secret,
                         @Value("${jwt.lifetime}") Duration lifetime) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.lifetime = lifetime;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .toList();
        claims.put("roles", roles);

        Date now = new Date();
        Date exp = new Date(now.getTime() + lifetime.toMillis());

        return Jwts.builder()
            .subject(userDetails.getUsername())
            .claims(claims)
            .issuedAt(now)
            .expiration(exp)
            .signWith(key, Jwts.SIG.HS256)
            .compact();
    }

    public String getUsername(String token) {
        return getAllClaims(token).getSubject();
    }

    public List<String> getRoles(String token) {
        return getAllClaims(token).get("roles", List.class);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}
