package com.makeorder.order.entity;

import com.makeorder.order.converter.PaymentMethodTypeConverter;
import com.makeorder.order.enums.PaymentMethodType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ORDER_EVENT_PAYMENT")
@NoArgsConstructor
@Getter
public class OrderEventPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_EVENT_PAYMENT_ID", updatable = false)
    private Long orderEventPaymentId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_EVENT_ID")
    private OrderEvent orderEvent;


    @Column(name = "METHOD_CD")
    @Convert(converter = PaymentMethodTypeConverter.class)
    private PaymentMethodType paymentMethod;

    @Column(name = "PAYMENT_PRICE")
    private Integer paymentPrice;
}