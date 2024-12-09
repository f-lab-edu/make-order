package com.makeorder.core.payment.service;

import com.makeorder.api.payment.dto.PaymentRequest;
import com.makeorder.core.order.entity.OrderEvent;
import com.makeorder.core.order.repository.OrderEventRepository;
import com.makeorder.core.payment.entity.Payment;
import com.makeorder.core.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentCommandService {

    private final PaymentRepository paymentRepository;
    private final OrderEventRepository orderEventRepository;

    public Payment createPayment(PaymentRequest paymentRequest, OrderEvent orderEvent) {
        return paymentRepository.save(Payment.builder()
                .orderEvent(orderEvent)
                .paymentMethod(paymentRequest.getPaymentMethod())
                .paymentPrice(paymentRequest.getPaymentPrice())
                .build());
    }
}
