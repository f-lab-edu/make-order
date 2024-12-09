package com.makeorder.core.payment.entity.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PaymentMethodType {
    CREDIT_CARD("C00", "신용카드"),
    KAKAO_PAY("P00", "카카오페이"),
    NAVER_PAY("P01", "네이버페이"),
    PAYCO("P02", "페이코");

    private final String dbCode;
    private final String label;

    PaymentMethodType(String dbCode, String label) {
        this.dbCode = dbCode;
        this.label = label;
    }

    public static PaymentMethodType fromDbCode(String dbCode){
        return Arrays.stream(PaymentMethodType.values())
                .filter(v -> v.getDbCode().equals(dbCode))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("결제 수단 %s가 존재하지 않습니다.", dbCode)));
    }
}
