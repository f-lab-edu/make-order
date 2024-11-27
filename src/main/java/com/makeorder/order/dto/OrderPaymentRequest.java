package com.makeorder.order.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.makeorder.order.enums.PaymentMethodType;
import lombok.Getter;

@Getter
public class OrderPaymentRequest {
    private Long orderId;
    private String methodCd;
    private Integer paymentPrice;
    private PaymentMethodType paymentMethod;

    @JsonCreator
    public OrderPaymentRequest(@JsonProperty("methodCd") String methodCd) {
        this.paymentMethod = PaymentMethodType.fromDbCode(methodCd);
    }
}
