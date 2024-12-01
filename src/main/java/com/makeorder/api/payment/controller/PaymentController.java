package com.makeorder.api.payment.controller;

import com.makeorder.api.payment.dto.PaymentRequest;
import com.makeorder.common.dto.BaseResponseBody;
import com.makeorder.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<BaseResponseBody> payments(@RequestBody PaymentRequest paymentRequest) {
        paymentService.pay(paymentRequest);

        return ResponseEntity.ok(BaseResponseBody.of(200, "Success"));
    }
}
