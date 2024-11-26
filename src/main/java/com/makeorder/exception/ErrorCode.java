package com.makeorder.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_JWT(BAD_REQUEST, "유효하지 않은 토큰입니다"),
    EXPIRED_JWT(BAD_REQUEST, "만료된 토큰입니다."),
    PAID_ORDER(BAD_REQUEST, "결제 완료된 주문입니다."),

    /* 401 UNAUTHORIZED : 인증되지 않은 요청 */
    MEMBER_PW_INVALID(UNAUTHORIZED, "유저의 PW가 일치하지 않습니다."),
    INVALID_ORDER_USER(UNAUTHORIZED, "주문한 유저가 아닙니다."),
    PAYMENT_PRICE_INVALID(UNAUTHORIZED, "결제 금액이 일치하지 않습니다."),

    /* 404 NOT_FOUND : Resource를 찾을 수 없음 */
    MEMBER_NOT_FOUND(NOT_FOUND, "해당 유저의 정보를 찾을 수 없습니다."),
    JWT_NOT_FOUND(NOT_FOUND, "JWT 토큰을 찾을 수 없습니다."),
    PRODUCT_NOT_FOUND(NOT_FOUND, "상품의 정보를 찾을 수 없습니다."),
    ORDER_NOT_FOUND(NOT_FOUND, "주문 정보를 찾을 수 없습니다."),
    PRODUCT_OUT_OF_STOCK(NOT_FOUND, "상품의 재고가 부족합니다."),

    /* 409 CONFLICT : Resource의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    MEMBER_EMAIL_DUPLICATE(CONFLICT, "중복된 이메일 주소입니다.");

    private HttpStatus httpStatus;
    private String message;
}
