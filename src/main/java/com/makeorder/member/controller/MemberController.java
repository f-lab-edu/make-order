package com.makeorder.member.controller;

import com.makeorder.common.dto.BaseResponseBody;
import com.makeorder.common.dto.DataResponse;
import com.makeorder.member.dto.SigninRequest;
import com.makeorder.member.dto.SigninResponse;
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

    @PostMapping("/sign-in")
    public ResponseEntity<DataResponse> signup(@RequestBody SigninRequest signinRequest) {
        SigninResponse signinResponse = memberService.signin(signinRequest);

        return ResponseEntity.ok(DataResponse.of(200, "Success", signinResponse));
    }
}