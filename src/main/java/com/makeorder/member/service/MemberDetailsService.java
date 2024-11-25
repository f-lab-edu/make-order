package com.makeorder.member.service;

import com.makeorder.exception.CustomException;
import com.makeorder.exception.ErrorCode;
import com.makeorder.jwt.UserPrincipal;
import com.makeorder.member.MemberRepository;
import com.makeorder.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findById(Long.parseLong(username))
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        return new UserPrincipal(member);
    }
}
