package com.makeorder.common.auth;

import com.makeorder.common.util.JwtUtil;
import com.makeorder.member.dto.MemberDetails;
import com.makeorder.core.member.entity.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = resolveAccessToken(request);

        if (accessToken != null && jwtUtil.validateAccessToken(accessToken)) {
            Authentication authentication = getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    public Authentication getAuthentication(String accessToken) {
        Long memberId = Long.parseLong(jwtUtil.getMemberId(accessToken));
        Member member = Member.builder().memberId(memberId).build();
        UserDetails principal = new MemberDetails(member);
        return new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
    }

    private String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtUtil.HEADER_STRING);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtUtil.TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}