package com.makeorder.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SigninResponse {

    private String accessToken;
    private MemberInfo memberInfo;

    public static SigninResponse of(String accessToken, MemberInfo memberInfo) {
        SigninResponse response = new SigninResponse();
        response.setAccessToken(accessToken);
        response.setMemberInfo(memberInfo);
        return response;
    }
}