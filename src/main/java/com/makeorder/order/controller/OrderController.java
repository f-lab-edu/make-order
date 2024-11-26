package com.makeorder.order.controller;

import com.makeorder.common.dto.BaseResponseBody;
import com.makeorder.common.dto.DataResponse;
import com.makeorder.order.dto.OrderPaymentRequest;
import com.makeorder.order.dto.OrderRequest;
import com.makeorder.order.dto.OrderResponse;
import com.makeorder.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<DataResponse> orders(@RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = orderService.order(orderRequest);

        return ResponseEntity.ok(DataResponse.of(200, "Success", orderResponse));
    }

    @PostMapping("/payments")
    public ResponseEntity<BaseResponseBody> payments(@RequestBody OrderPaymentRequest orderPaymentRequest) {
        orderService.pay(orderPaymentRequest);

        return ResponseEntity.ok(BaseResponseBody.of(200, "Success"));
    }
}
