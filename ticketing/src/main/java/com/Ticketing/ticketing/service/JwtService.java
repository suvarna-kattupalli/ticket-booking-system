package com.Ticketing.ticketing.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private Key key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)

        );
        System.out.println("JWT UTIL SECRET = " + secret);
        System.out.println("JWT UTIL KEY = " + key);
    }

    public String generateToken(String email, String role) {


        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + expiration)
                )
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

    }

    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean validateToken(String token, String email) {

        try {
            return extractEmail(token).equals(email)
                    && !extractClaims(token)
                    .getExpiration()
                    .before(new Date());

        } catch (Exception e) {
            return false;
        }
    }

    private Claims extractClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}