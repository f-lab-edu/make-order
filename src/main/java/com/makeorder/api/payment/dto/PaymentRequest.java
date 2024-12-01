package com.makeorder.api.payment.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.makeorder.core.payment.entity.enums.PaymentMethodType;
import lombok.Getter;

@Getter
public class PaymentRequest {
    private Long orderId;
    private String methodCd;
    private Integer paymentPrice;
    private PaymentMethodType paymentMethod;

    @JsonCreator
    public PaymentRequest(@JsonProperty("methodCd") String methodCd) {
        this.paymentMethod = PaymentMethodType.fromDbCode(methodCd);
    }
}
