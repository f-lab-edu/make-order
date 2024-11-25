package com.makeorder.jwt;

import com.makeorder.exception.CustomException;
import com.makeorder.member.entity.Member;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

import static com.makeorder.exception.ErrorCode.EXPIRED_JWT;
import static com.makeorder.exception.ErrorCode.INVALID_JWT;

@Slf4j
@Component
public class JwtUtil {

    public String getAccessToken(final Key ACCESS_SECRET, final long ACCESS_EXPIRATION, Member member) {
        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(member))
                .setSubject(String.valueOf(member.getMemberId()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION))
                .signWith(ACCESS_SECRET, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getRefreshToken(final Key REFRESH_SECRET, final long REFRESH_EXPIRATION, Member member) {
        return Jwts.builder()
                .setHeader(createHeader())
                .setSubject(String.valueOf(member.getMemberId()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION))
                .signWith(REFRESH_SECRET, SignatureAlgorithm.HS256)
                .compact();
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        return header;
    }

    private Map<String, Object> createClaims(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("Identifier", member.getMemberId());
        return claims;
    }

    public TokenStatus getTokenStatus(String token, Key secretKey) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return TokenStatus.AUTHENTICATED;
        } catch (ExpiredJwtException e) {
            log.error(EXPIRED_JWT.getMessage());
            return TokenStatus.EXPIRED;
        } catch (JwtException e) {
            log.error(INVALID_JWT.getMessage());
            throw new CustomException(INVALID_JWT);
        }
    }

    public String resolveTokenFromCookie(Cookie[] cookies, JwtRule tokenPrefix) {
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(tokenPrefix.getValue()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse("");
    }

    public Key getSigningKey(String secretKey) {
        String encodedKey = encodeToBase64(secretKey);
        return Keys.hmacShaKeyFor(encodedKey.getBytes(StandardCharsets.UTF_8));
    }

    private String encodeToBase64(String secretKey) {
        return Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public Cookie resetToken(JwtRule tokenPrefix) {
        Cookie cookie = new Cookie(tokenPrefix.getValue(), null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        return cookie;
    }
}