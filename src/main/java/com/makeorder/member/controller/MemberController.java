package com.makeorder.member.controller;

import com.makeorder.common.dto.BaseResponseBody;
import com.makeorder.member.dto.SignupRequest;
import com.makeorder.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    public ResponseEntity<BaseResponseBody> signup(@RequestBody SignupRequest signupRequest) {
        memberService.signup(signupRequest);

        return ResponseEntity.ok(BaseResponseBody.of(200, "Success"));
    }
}