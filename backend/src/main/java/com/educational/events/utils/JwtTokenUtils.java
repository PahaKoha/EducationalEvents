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
    private final Duration accessLifetime;
    private final Duration refreshLifetime;

    public enum JwtTokenType { ACCESS, REFRESH }

    public JwtTokenUtils(@Value("${jwt.secret}") String secret,
                         @Value("${jwt.access-lifetime}") Duration accessLifetime,
                         @Value("${jwt.refresh-lifetime}") Duration refreshLifetime) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessLifetime = accessLifetime;
        this.refreshLifetime = refreshLifetime;
    }

    public String generateToken(UserDetails userDetails, JwtTokenType type) {
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .toList();
        claims.put("roles", roles);

        claims.put("typ", type.name().toLowerCase());

        Date now = new Date();
        Date exp = new Date(now.getTime() + getTtl(type).toMillis());

        return Jwts.builder()
            .subject(userDetails.getUsername())
            .claims(claims)
            .issuedAt(now)
            .expiration(exp)
            .signWith(key, Jwts.SIG.HS256)
            .compact();
    }

    public String generateAccess(UserDetails user) {
        return generateToken(user, JwtTokenType.ACCESS);
    }

    public String generateRefresh(UserDetails user) {
        return generateToken(user, JwtTokenType.REFRESH);
    }

    public boolean isRefresh(String token) {
        return "refresh".equals(getAllClaims(token).get("typ", String.class));
    }

    public boolean isAccess(String token) {
        return "access".equals(getAllClaims(token).get("typ", String.class));
    }

    public String getUsername(String token) {
        return getAllClaims(token).getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<String> getRoles(String token) {
        return getAllClaims(token).get("roles", List.class);
    }

    private Duration getTtl(JwtTokenType type) {
        return (type == JwtTokenType.ACCESS) ? accessLifetime : refreshLifetime;
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}
