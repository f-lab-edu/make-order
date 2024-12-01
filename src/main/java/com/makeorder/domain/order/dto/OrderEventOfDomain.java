package com.makeorder.domain.order.dto;

import com.makeorder.core.order.entity.OrderEvent;
import com.makeorder.core.order.entity.enums.OrderStatusType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderEventOfDomain {

    private Long orderEventId;
    private Long orderId;
    private OrderStatusType orderStatus;

    public static OrderEventOfDomain of(OrderEvent orderEvent) {
        OrderEventOfDomain dto = new OrderEventOfDomain();
        dto.setOrderEventId(orderEvent.getOrderEventId());
        dto.setOrderId(orderEvent.getOrder().getOrderId());
        dto.setOrderStatus(orderEvent.getOrderStatus());
        return dto;
    }
}
