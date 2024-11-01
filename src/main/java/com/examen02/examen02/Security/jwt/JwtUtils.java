package com.examen02.examen02.Security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Component
public class JwtUtils {

    @Value("${jwt.secret.key}")
    private String secretKey;
    @Value("${jwt.time.expiration}")
    private String timeExpiration;

    // Clave para firmar JWT
    private final Key signatureKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    // Método para generar un token de acceso
    public String generateAccessToken(String username){

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ Long.parseLong(timeExpiration)))
                .signWith(signatureKey, SignatureAlgorithm.HS512)
                .compact();
    }
    // Método para validar el token
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(signatureKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (Exception e) {
            log.error("Token inválido, error: ".concat(e.getMessage()));
            return false;
        }
    }

    // Métodos para obtener información del token
    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(signatureKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public <T> T getClaim(String token, Function<Claims, T > claimsTFunction){
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }
    public String getUsername(String token){
        return getClaim(token, Claims::getSubject);
    }

}
