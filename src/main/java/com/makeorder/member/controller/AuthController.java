package com.makeorder.member.controller;

import com.makeorder.common.dto.DataResponse;
import com.makeorder.jwt.service.JwtService;
import com.makeorder.member.dto.SigninRequest;
import com.makeorder.member.entity.Member;
import com.makeorder.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final MemberService userService;
    private final JwtService jwtService;

    @PostMapping("/auth")
    public ResponseEntity<DataResponse> generateToken(HttpServletResponse response, @RequestBody SigninRequest tokenRequest) {
        Member requestUser = userService.findMemberByEmail(tokenRequest.getEmail());
        jwtService.generateAccessToken(response, requestUser);
        jwtService.generateRefreshToken(response, requestUser);

//        AuthResponse authResponse = userService.getUserAuthInfo(requestUser.getIdentifier());

        return ResponseEntity.ok(DataResponse.of(200, "Success", requestUser));
    }

    @PostMapping("/auth2")
    public ResponseEntity<DataResponse> generateToken2(HttpServletResponse response, @RequestBody SigninRequest tokenRequest) {
        Member requestUser = userService.findMemberByEmail(tokenRequest.getEmail());
//        jwtService.validateUser(requestUser);
//        jwtService.generateAccessToken(response, requestUser);
//        jwtService.generateRefreshToken(response, requestUser);

//        AuthResponse authResponse = userService.getUserAuthInfo(requestUser.getIdentifier());

        return ResponseEntity.ok(DataResponse.of(200, "Success", requestUser));
    }
}