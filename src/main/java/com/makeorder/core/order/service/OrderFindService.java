package com.makeorder.core.order.service;

import com.makeorder.core.order.entity.Order;
import com.makeorder.core.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderFindService {

    private final OrderRepository orderRepository;

    public Optional<Order> findOrder(Long orderId) {
        return orderRepository.findById(orderId);
    }
}
