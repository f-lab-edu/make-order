package com.makeorder.member.service;

import com.makeorder.common.util.JwtUtil;
import com.makeorder.exception.CustomException;
import com.makeorder.exception.ErrorCode;
import com.makeorder.core.member.repository.MemberRepository;
import com.makeorder.member.dto.MemberInfo;
import com.makeorder.member.dto.SigninRequest;
import com.makeorder.member.dto.SigninResponse;
import com.makeorder.member.dto.SignupRequest;
import com.makeorder.core.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public Long signup(SignupRequest signupRequest) {
        validateEmailNotDuplicated(signupRequest.getEmail());

        return memberRepository.save(Member.builder()
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .name(signupRequest.getName())
                .phone(signupRequest.getPhone())
                        .build())
                .getMemberId();
    }

    @Transactional(readOnly = true)
    public void validateEmailNotDuplicated(String email) {
        if (memberRepository.existsMemberByEmail(email)) {
            throw new CustomException(ErrorCode.MEMBER_EMAIL_DUPLICATE);
        }
    }

    @Transactional(readOnly = true)
    public SigninResponse signin(SigninRequest signinRequest) {
        Member member = memberRepository.findByEmail(signinRequest.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if (passwordEncoder.matches(signinRequest.getPassword(), member.getPassword())) {
            String accessToken = jwtUtil.generateAccessToken(member.getMemberId());
            return SigninResponse.of(accessToken, MemberInfo.of(member.getEmail(), member.getName(), member.getPhone()));
        }
        throw new CustomException(ErrorCode.MEMBER_PW_INVALID);
    }
}
