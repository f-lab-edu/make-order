package com.makeorder.core.order.service;

import com.makeorder.core.order.entity.Order;
import com.makeorder.core.order.entity.OrderEvent;
import com.makeorder.core.order.entity.enums.OrderStatusType;
import com.makeorder.core.order.repository.OrderEventRepository;
import com.makeorder.domain.order.mapper.OrderEventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventCommandService {

    private final OrderEventRepository orderEventRepository;
    private final OrderEventMapper orderEventMapper;

    public OrderEvent createOrderEvent(Order order, OrderStatusType orderStatus) {
        return orderEventRepository.save(orderEventMapper.toEntity(order, orderStatus));
    }
}
