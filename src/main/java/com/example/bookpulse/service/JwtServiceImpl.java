package com.example.bookpulse.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service("jwtService")
public class JwtServiceImpl implements JwtService {

    // HS256에 사용할 대칭 키 생성
    private final Key secretKey = Keys.hmacShaKeyFor("inydrhs9oe9rt3i586fg74thjylb7n6n5dhr9900kee3".getBytes());

    @Override
    public String getToken(String key, Object value) {
        Date expTime = new Date();
        expTime.setTime(expTime.getTime() + 1000 * 60 * 30); // 30분 동안 유효

        return Jwts.builder()
                .setSubject("subject")
                .claim(key, value)
                .setExpiration(expTime)
                .signWith(secretKey, SignatureAlgorithm.HS256) // 대칭 키와 HS256 사용
                .compact();
    }

    @Override
    public Claims getClams(String token) {
        // 토큰에서 클레임 추출
        return Jwts.parserBuilder()
                .setSigningKey(secretKey) // 서명 키를 사용해 검증
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public boolean isValid(String token) {
        // 토큰의 유효성 검증
        try {
            getClams(token); // 유효한지 검증
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int getId(String token) {
        Claims claims = getClams(token);
        return Integer.parseInt(claims.get("id").toString());
    }
}
