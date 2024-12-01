package com.makeorder.domain.member.dto;

import com.makeorder.core.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberOfDomain {
    private Long memberId;
    private String email;
    private String password;
    private String name;
    private String phone;

    public static MemberOfDomain of(Member member) {
        MemberOfDomain dto = new MemberOfDomain();
        dto.setMemberId(member.getMemberId());
        dto.setEmail(member.getEmail());
        dto.setPassword(member.getPassword());
        dto.setName(member.getName());
        dto.setPhone(member.getPhone());
        return dto;
    }
}
