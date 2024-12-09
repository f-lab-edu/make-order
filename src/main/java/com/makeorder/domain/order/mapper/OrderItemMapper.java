package com.makeorder.domain.order.mapper;

import com.makeorder.core.order.entity.Order;
import com.makeorder.core.order.entity.OrderItem;
import com.makeorder.product.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

    public OrderItem toEntity(Product product, Order order, Integer quantity) {
        return OrderItem.builder()
                .order(order)
                .product(product)
                .productName(product.getName())
                .productPrice(product.getPrice())
                .paymentPrice(product.getPrice())
                .quantity(quantity)
                .isFreeDelivery(product.getIsFreeDelivery())
                .deliveryFee(product.getDeliveryFee())
                .build();
    }
}
