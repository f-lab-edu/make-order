package com.makeorder.domain.order.mapper;

import com.makeorder.core.order.entity.Order;
import com.makeorder.core.order.entity.OrderEvent;
import com.makeorder.core.order.entity.enums.OrderStatusType;
import org.springframework.stereotype.Component;

@Component
public class OrderEventMapper {

    public OrderEvent toEntity(Order order, OrderStatusType orderStatus) {
        return OrderEvent.builder()
                .order(order)
                .orderStatus(orderStatus)
                .build();
    }
}
