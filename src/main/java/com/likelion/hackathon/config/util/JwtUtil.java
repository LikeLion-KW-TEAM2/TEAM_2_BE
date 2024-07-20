package com.likelion.hackathon.config.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {
    private SecretKey secretKey;

    // JwtUtil이 생성될 때 application.properties의 secretkey를 가져오도록 함
    public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
        // string으로 저장된 secretkey를 secretkey객체로 변환
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    // token에서 username을 추출(parsing)
    public String getUsername(String token) {
        // verifyWith으로 우리 서버에서 생성된 게 맞는지 검증
        // 이후 builder패턴으로 리턴받은 뒤에 페이로드에서 username을 추출
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("userId",
                String.class);
    }

    // token에서 role을 추출(parsing)
    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role",
                String.class);
    }

    // token에서 만료 일자를 추출(parsing)
    public Boolean isExpired(String token) {
        // 페이로드에서 만료시간을 추출할 때에는 getExpiration으로 현재 시간(new Date())보다 이전 인지 판별 후 boolean
        // 리턴
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration()
                .before(new Date());
    }

    // 새 토큰을 생성
    public String createJwt(String userId, String role, Long expiredMs) {
        return Jwts.builder()
                .claim("userId", userId) // 페이로드에 username추가
                .claim("role", role) // 페이로드에 role추가
                .issuedAt(new Date(System.currentTimeMillis())) // token이 발급되는 시점 기록
                .expiration(new Date(System.currentTimeMillis() + expiredMs)) // 만료 기점 기록
                .signWith(secretKey) // 암호화 진행
                .compact(); // 토큰 최종 발행
    }
}
