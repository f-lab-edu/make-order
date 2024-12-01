package com.makeorder.core.order.service;

import com.makeorder.api.order.dto.OrderRequest;
import com.makeorder.core.member.entity.Member;
import com.makeorder.core.order.entity.Order;
import com.makeorder.core.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderCommandService {

    private final OrderRepository orderRepository;

    public Order createOrder(OrderRequest orderRequest, Member member, int totalPrice) {
        return orderRepository.save(Order.builder()
                .member(member)
                .rcpntName(orderRequest.getRcpntName())
                .rcpntAddress(orderRequest.getRcpntAddress())
                .rcpntPhone(orderRequest.getRcpntPhone())
                .comments(orderRequest.getComments())
                .totalPrice(totalPrice)
                .build());
    }
}
