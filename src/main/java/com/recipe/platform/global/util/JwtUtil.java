package com.recipe.platform.global.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    private final SecretKey secretKey;
    private final long accessTokenValidityInMilliseconds;

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-validity}") long accessTokenValidity
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidityInMilliseconds = accessTokenValidity;
    }

    // 액세스 토큰 생성
    public String generateAccessToken(Long userId, String email) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + accessTokenValidityInMilliseconds);

        return Jwts.builder()
                .subject(userId.toString())
                .claim("email", email)
                .issuedAt(now)
                .expiration(validity)
                .signWith(secretKey)
                .compact();
    }

    // 토큰에서 userId 추출
    public Long getUserId(String token) {
        Claims claims = parseClaims(token);
        return Long.parseLong(claims.getSubject());
    }

    // 토큰에서 email 추출
    public String getEmail(String token) {
        Claims claims = parseClaims(token);
        return claims.get("email", String.class);
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 토큰 파싱
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
