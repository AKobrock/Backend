package com.usuarios.Demo.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.usuarios.Demo.model.AdminModel;
import com.usuarios.Demo.model.UserModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration}")
    private long expirationMs;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /** ================================
     * 🔐 TOKEN PARA ADMIN
     * ================================= */
    public String generateToken(AdminModel admin) {
        return buildToken(
                admin.getEmail(),
                "ADMIN"
        );
    }

    /** ================================
     * 👤 TOKEN PARA USUARIO NORMAL
     * ================================= */
    public String generateToken(UserModel user) {
        return buildToken(
                user.getEmail(),
                "USER"
        );
    }

    /** ================================
     * 🧬 MÉTODO GENÉRICO PARA CREAR TOKENS
     * ================================= */
    private String buildToken(String email, String role) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setSubject(email)
                .claim("rol", role) // ← ojo! aquí SI es "rol"
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ===============================
    // 📌 OBTENER CAMPOS DEL TOKEN
    // ===============================

    public String getUsernameFromToken(String token) {
        return getAllClaims(token).getSubject();
    }

    public String getRolFromToken(String token) {
        return getAllClaims(token).get("rol", String.class); // ← estaba "roll"
    }

    // ===============================
    // 📌 VALIDAR TOKEN
    // ===============================

    public boolean isTokenValid(String token) {
        try {
            return getAllClaims(token).getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
