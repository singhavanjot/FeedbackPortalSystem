package com.Portal.FeedbackPortal.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key secretKey;
    private final long validityMs;

    public JwtTokenProvider(@Value("${jwt.secret:e3ff5f077839c1331b1d893a728246685cb7dba9e3a77bffe7d52eaccf660988}") String secret,
                            @Value("${jwt.expiration:86400000}") long validityMs) {
        // ensure secret is long enough; Keys.hmacShaKeyFor requires enough entropy
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.validityMs = validityMs;
    }

    public String generateToken(org.springframework.security.core.userdetails.User userDetails) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityMs);

        // ek hi role le lo (agar multiple ho to pehla le lenge)
        String role = userDetails.getAuthorities().iterator().next().getAuthority(); 
        // yaha authority already ROLE_ADMIN / ROLE_USER hoti hai

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("role", role) // ab yaha galat prefix nahi aayega
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }


    public String getUsernameFromJWT(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public String getRoleFromJWT(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody().get("role", String.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Invalid JWT: " + e.getMessage());
            return false;
        }
    }
}
