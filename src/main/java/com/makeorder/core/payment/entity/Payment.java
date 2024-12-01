package com.makeorder.core.payment.entity;

import com.makeorder.core.order.entity.OrderEvent;
import com.makeorder.core.payment.entity.enums.PaymentMethodType;
import com.makeorder.core.payment.entity.converter.PaymentMethodTypeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ORDER_EVENT_PAYMENT")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_EVENT_PAYMENT_ID", updatable = false)
    private Long paymentId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_EVENT_ID")
    private OrderEvent orderEvent;


    @Column(name = "METHOD_CD")
    @Convert(converter = PaymentMethodTypeConverter.class)
    private PaymentMethodType paymentMethod;

    @Column(name = "PAYMENT_PRICE")
    private Integer paymentPrice;
}