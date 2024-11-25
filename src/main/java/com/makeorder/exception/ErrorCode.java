package com.makeorder.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    MEMBER_NOT_FOUND(NOT_FOUND, "해당 유저의 정보를 찾을 수 없습니다."),
    INVALID_JWT(UNAUTHORIZED, "유효하지 않은 토큰입니다"),
    EXPIRED_JWT(UNAUTHORIZED, "만료된 토큰입니다."),
    JWT_NOT_FOUND(NOT_FOUND, "JWT 토큰을 찾을 수 없습니다.");

    private HttpStatus httpStatus;
    private String message;
}
