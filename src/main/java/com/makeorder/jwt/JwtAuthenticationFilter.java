package com.makeorder.jwt;

import com.makeorder.jwt.service.JwtService;
import com.makeorder.member.entity.Member;
import com.makeorder.member.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final MemberService memberService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = jwtService.resolveTokenFromCookie(request, JwtRule.ACCESS_PREFIX);
        if (jwtService.validateAccessToken(accessToken)) {
            setAuthenticationToContext(accessToken);
            filterChain.doFilter(request, response);
            return;
        }

        String refreshToken = jwtService.resolveTokenFromCookie(request, JwtRule.REFRESH_PREFIX);
        Member member = findUserByRefreshToken(refreshToken);

        if (jwtService.validateRefreshToken(refreshToken, member.getMemberId())) {
            String reissuedAccessToken = jwtService.generateAccessToken(response, member);
            jwtService.generateRefreshToken(response, member);

            setAuthenticationToContext(reissuedAccessToken);
            filterChain.doFilter(request, response);
            return;
        }

        jwtService.logout(member, response);
    }

    private boolean isPermittedURI(String requestURI) {
        return Arrays.stream(new String[] {"/api/auth/**", "/login"})
                .anyMatch(permitted -> {
                    String replace = permitted.replace("*", "");
                    return requestURI.contains(replace) || replace.contains(requestURI);
                });
    }

    private Member findUserByRefreshToken(String refreshToken) {
        String identifier = jwtService.getIdentifierFromRefresh(refreshToken);
        return memberService.findMemberByMemberId(Long.parseLong(identifier));
    }

    private void setAuthenticationToContext(String accessToken) {
        Authentication authentication = jwtService.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}