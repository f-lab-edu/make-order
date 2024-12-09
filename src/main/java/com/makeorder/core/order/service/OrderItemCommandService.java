package com.makeorder.core.order.service;

import com.makeorder.core.order.entity.OrderItem;
import com.makeorder.core.order.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemCommandService {

    private final OrderItemRepository orderItemRepository;

    public List<OrderItem> saveAll(List<OrderItem> orderItems) {
        return orderItemRepository.saveAll(orderItems);
    }
}
