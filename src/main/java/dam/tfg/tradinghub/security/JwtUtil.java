package dam.tfg.tradinghub.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Slf4j

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secretKey;

    private static final long EXPIRATION_MILLIS = 1000L * 60 * 60 * 72; // 72 horas

    private Key key;

    public String generateToken(String userId) {
        return Jwts.builder()
                .setHeader(Map.of("typ", "JWT", "alg", "HS256"))
                .setSubject(userId)
                .setIssuer("trading-hub")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MILLIS))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String extractUserId(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, String userId) {
        final String id = extractUserId(token);
        return (id.equals(userId) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(this.key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            log.error("Error al extraer los claims del token JWT: {}", e.getMessage());
            Map.Entry<String, Object> errorEntry = Map.entry("error", "Token inválido o expirado");
            return Jwts.claims(Map.of(errorEntry.getKey(), errorEntry.getValue()));
        }
    }

}
