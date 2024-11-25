package com.makeorder.common.util;

import com.makeorder.exception.CustomException;
import com.makeorder.exception.ErrorCode;
import com.makeorder.member.service.MemberDetailsService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

@Slf4j
@Component
public class JwtUtil {

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    private final Key ACCESS_SECRET_KEY;
    private final long ACCESS_EXPIRATION;

    private final MemberDetailsService memberDetailsService;

    public JwtUtil(@Value("${jwt.access-secret}") String ACCESS_SECRET_KEY,
                   @Value("${jwt.access-expiration}") long ACCESS_EXPIRATION, MemberDetailsService memberDetailsService) {
        this.ACCESS_SECRET_KEY = getSigningKey(ACCESS_SECRET_KEY);
        this.ACCESS_EXPIRATION = ACCESS_EXPIRATION;
        this.memberDetailsService = memberDetailsService;
    }

    public String generateAccessToken(Long memberId) {
        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(memberId))
                .setSubject(String.valueOf(memberId))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION))
                .signWith(ACCESS_SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(ACCESS_SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
            throw new CustomException(ErrorCode.EXPIRED_JWT);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    public String getMemberId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(ACCESS_SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        return header;
    }

    private Map<String, Object> createClaims(Long memberId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("Identifier", memberId);
        return claims;
    }

    public Key getSigningKey(String secretKey) {
        String encodedKey = encodeToBase64(secretKey);
        return Keys.hmacShaKeyFor(encodedKey.getBytes(StandardCharsets.UTF_8));
    }

    private String encodeToBase64(String secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getBytes());
    }
}
