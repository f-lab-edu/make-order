package com.makeorder.order.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum OrderStatusType {
    ORDER_ACCEPT("A00", "주문 접수"),
    ORDER_CANCEL("A09", "주문 취소"),
    PAYMENT_COMPLETE("P00", "결제 완료"),
    PAYMENT_FAIL("P09", "결제 실패"),
    PRODUCT_PREPARE("D00", "상품 준비 중"),
    DELIVERY_PREPARE("D01", "배송 준비 중"),
    DELIVERING("D02", "배송 중"),
    DELIVERY_COMPLETE("D03", "배송 완료"),
    PURCHASE_COMPLETE("S00", "구매 완료");

    private final String dbCode;
    private final String label;

    OrderStatusType(String dbCode, String label) {
        this.dbCode = dbCode;
        this.label = label;
    }

    public static OrderStatusType fromDbCode(String dbCode){
        return Arrays.stream(OrderStatusType.values())
                .filter(v -> v.getDbCode().equals(dbCode))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("주문 상태 %s가 존재하지 않습니다.", dbCode)));
    }
}