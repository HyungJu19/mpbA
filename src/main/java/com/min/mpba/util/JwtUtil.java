package com.min.mpba.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-expiration}")
    private long accessExpirationTime; // Access Token 만료 시간 (예: 15분)

    @Value("${jwt.refresh-expiration}")
    private long refreshExpirationTime; // Refresh Token 만료 시간 (예: 7일)

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // ✅ Access Token 생성
    public String generateAccessToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ Refresh Token 생성
    public String generateRefreshToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ 토큰 검증 및 사용자 ID 반환
    public String validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String userId = claims.getSubject();
            System.out.println("✅ [JwtUtil] 토큰에서 추출한 userId: " + userId);
            return userId;

        } catch (ExpiredJwtException e) {
            System.out.println("🔴 [JwtUtil] 토큰 만료됨: " + e.getMessage());
            throw e; // ✅ 예외를 던져 JwtAuthFilter에서 처리할 수 있도록 변경
        } catch (JwtException e) {
            System.out.println("🔴 [JwtUtil] 유효하지 않은 토큰: " + e.getMessage());
            throw e; // ✅ 마찬가지로 예외를 던짐
        }
    }


    public String extractUsername(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            System.out.println("🔴 [토큰 만료] " + e.getMessage());
            throw e; // 컨트롤러에서 처리하도록 예외 그대로 던짐
        } catch (JwtException e) {
            System.out.println("🔴 [유효하지 않은 토큰] " + e.getMessage());
            throw e; // 컨트롤러에서 처리하도록 예외 그대로 던짐
        }
    }



    // ✅ Access Token이 만료되었는지 확인
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true; // 만료된 경우 true 반환
        }
    }
}
