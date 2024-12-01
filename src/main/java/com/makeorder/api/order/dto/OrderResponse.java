package com.makeorder.api.order.dto;

import com.makeorder.core.order.entity.Order;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {
    private Long orderId;
    private Integer paymentPrice;

    public static OrderResponse of(Order order) {
        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getOrderId());
        response.setPaymentPrice(order.getTotalPrice());
        return response;
    }
}
