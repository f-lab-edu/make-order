package com.makeorder.core.member.service;

import com.makeorder.core.member.entity.Member;
import com.makeorder.core.member.repository.MemberRepository;
import com.makeorder.exception.CustomException;
import com.makeorder.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberFindService {

    private final MemberRepository memberRepository;

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
