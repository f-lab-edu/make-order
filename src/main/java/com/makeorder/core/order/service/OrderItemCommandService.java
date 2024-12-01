package com.makeorder.core.order.service;

import com.makeorder.core.order.entity.Order;
import com.makeorder.core.order.entity.OrderItem;
import com.makeorder.core.order.repository.OrderItemRepository;
import com.makeorder.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemCommandService {

    private final OrderItemRepository orderItemRepository;

    public OrderItem createOrderItem(Product product, Order order, Integer quantity) {
        return orderItemRepository.save(OrderItem.builder()
                .order(order)
                .product(product)
                .productName(product.getName())
                .productPrice(product.getPrice())
                .paymentPrice(product.getPrice())
                .quantity(quantity)
                .isFreeDelivery(product.getIsFreeDelivery())
                .deliveryFee(product.getDeliveryFee())
                .build());
    }
}
