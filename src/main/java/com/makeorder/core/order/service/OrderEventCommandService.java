package com.makeorder.core.order.service;

import com.makeorder.core.order.entity.Order;
import com.makeorder.core.order.entity.OrderEvent;
import com.makeorder.core.order.entity.enums.OrderStatusType;
import com.makeorder.core.order.repository.OrderEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventCommandService {

    private final OrderEventRepository orderEventRepository;

    // irr 안 써도 아이디값 반환할까?
    public OrderEvent createOrderEvent(Order order, OrderStatusType orderStatus) {
        return orderEventRepository.save(OrderEvent.builder()
                .order(order)
                .orderStatus(orderStatus)
                .build());
    }
}
