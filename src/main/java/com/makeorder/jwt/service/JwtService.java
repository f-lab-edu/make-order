package com.makeorder.jwt.service;

import com.makeorder.exception.CustomException;
import com.makeorder.exception.ErrorCode;
import com.makeorder.jwt.*;
import com.makeorder.jwt.repository.TokenRepository;
import com.makeorder.member.entity.Member;
import com.makeorder.member.service.MemberDetailsService;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;

import static com.makeorder.jwt.JwtRule.*;

@Slf4j
@Transactional(readOnly = true)
@Service
public class JwtService {

    private final MemberDetailsService memberDetailsService;
    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;

    private final Key ACCESS_SECRET_KEY;
    private final Key REFRESH_SECRET_KEY;
    private final long ACCESS_EXPIRATION;
    private final long REFRESH_EXPIRATION;

    public JwtService(MemberDetailsService memberDetailsService, JwtUtil jwtUtil, TokenRepository tokenRepository,
                      @Value("${jwt.access-secret}") String ACCESS_SECRET_KEY,
                      @Value("${jwt.refresh-secret}") String REFRESH_SECRET_KEY,
                      @Value("${jwt.access-expiration}") long ACCESS_EXPIRATION,
                      @Value("${jwt.refresh-expiration}") long REFRESH_EXPIRATION) {
        this.memberDetailsService = memberDetailsService;
        this.jwtUtil = jwtUtil;
        this.tokenRepository = tokenRepository;
        this.ACCESS_SECRET_KEY = jwtUtil.getSigningKey(ACCESS_SECRET_KEY);
        this.REFRESH_SECRET_KEY = jwtUtil.getSigningKey(REFRESH_SECRET_KEY);
        this.ACCESS_EXPIRATION = ACCESS_EXPIRATION;
        this.REFRESH_EXPIRATION = REFRESH_EXPIRATION;
    }

    public String generateAccessToken(HttpServletResponse response, Member member) {
        String accessToken = jwtUtil.getAccessToken(ACCESS_SECRET_KEY, ACCESS_EXPIRATION, member);
        ResponseCookie cookie = setTokenToCookie(ACCESS_PREFIX.getValue(), accessToken, ACCESS_EXPIRATION / 1000);
        response.addHeader(JWT_ISSUE_HEADER.getValue(), cookie.toString());

        return accessToken;
    }

    @Transactional
    public String generateRefreshToken(HttpServletResponse response, Member member) {
        String refreshToken = jwtUtil.getRefreshToken(REFRESH_SECRET_KEY, REFRESH_EXPIRATION, member);
        ResponseCookie cookie = setTokenToCookie(REFRESH_PREFIX.getValue(), refreshToken, REFRESH_EXPIRATION / 1000);
        response.addHeader(JWT_ISSUE_HEADER.getValue(), cookie.toString());

        tokenRepository.save(new Token(member.getMemberId(), refreshToken));
        return refreshToken;
    }

    private ResponseCookie setTokenToCookie(String tokenPrefix, String token, long maxAgeSeconds) {
        return ResponseCookie.from(tokenPrefix, token)
                .path("/")
                .maxAge(maxAgeSeconds)
//                .httpOnly(true)
//                .sameSite("None")
//                .secure(true)
                .build();
    }

    public boolean validateAccessToken(String token) {
        return jwtUtil.getTokenStatus(token, ACCESS_SECRET_KEY) == TokenStatus.AUTHENTICATED;
    }

    public boolean validateRefreshToken(String token, Long identifier) {
        boolean isRefreshValid = jwtUtil.getTokenStatus(token, REFRESH_SECRET_KEY) == TokenStatus.AUTHENTICATED;

        Token storedToken = tokenRepository.findByMemberId(identifier);
        boolean isTokenMatched = storedToken.getRefreshToken().equals(token);

        return isRefreshValid && isTokenMatched;
    }

    public String resolveTokenFromCookie(HttpServletRequest request, JwtRule tokenPrefix) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new CustomException(ErrorCode.JWT_NOT_FOUND);
        }
        return jwtUtil.resolveTokenFromCookie(cookies, tokenPrefix);
    }

    public Authentication getAuthentication(String token) {
        UserDetails principal = memberDetailsService.loadUserByUsername(getMemberId(token, ACCESS_SECRET_KEY));
        return new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
    }

    private String getMemberId(String token, Key secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getIdentifierFromRefresh(String refreshToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(REFRESH_SECRET_KEY)
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.INVALID_JWT);
        }
    }

    public void logout(Member requestUser, HttpServletResponse response) {
//        tokenRepository.deleteById(requestUser.getMemberId());

        Cookie accessCookie = jwtUtil.resetToken(ACCESS_PREFIX);
        Cookie refreshCookie = jwtUtil.resetToken(REFRESH_PREFIX);

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);
    }
}