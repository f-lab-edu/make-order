package com.makeorder.member.service;

import com.makeorder.exception.CustomException;
import com.makeorder.exception.ErrorCode;
import com.makeorder.member.MemberRepository;
import com.makeorder.member.dto.SignupRequest;
import com.makeorder.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public Long signup(SignupRequest signupRequest) {
        validateEmailNotDuplicated(signupRequest.getEmail());

        return memberRepository.save(Member.builder()
                .email(signupRequest.getEmail())
                .password(signupRequest.getPassword())
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
}
