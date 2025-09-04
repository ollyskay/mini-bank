package org.example.bankprojectibk.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    public String generateToken(String email, Map<String, Object> extractClaims) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + JwtConstant.ACCESS_TOKEN_EXPIRATION_TIME);

        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS256, JwtConstant.SECRET_KEY)
                .compact();
    }
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    public String extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", String.class));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean validateToken(String token, String email) {
        final String tokenEmail = extractEmail(token);
        return (tokenEmail.equals(email) && !isTokenExpired(token));
    }
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(JwtConstant.SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
