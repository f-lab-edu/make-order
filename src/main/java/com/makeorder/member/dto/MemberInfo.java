package com.makeorder.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInfo {
    String email;
    String name;
    String phone;

    public static MemberInfo of(String email, String name, String phone) {
        MemberInfo info = new MemberInfo();
        info.setEmail(email);
        info.setName(name);
        info.setPhone(phone);
        return info;
    }
}