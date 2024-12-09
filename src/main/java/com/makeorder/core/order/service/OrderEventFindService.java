package com.makeorder.core.order.service;

import com.makeorder.core.order.entity.Order;
import com.makeorder.core.order.entity.OrderEvent;
import com.makeorder.core.order.entity.enums.OrderStatusType;
import com.makeorder.core.order.repository.OrderEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderEventFindService {

    private final OrderEventRepository orderEventRepository;

    public Optional<OrderEvent> findOrderEventByOrderAndOrderStatus(Order order, OrderStatusType orderStatus) {
        return orderEventRepository.findByOrderAndOrderStatus(order, orderStatus);
    }
}
