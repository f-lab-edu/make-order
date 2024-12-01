package com.makeorder.core.order.repository;

import com.makeorder.core.order.entity.Order;
import com.makeorder.core.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrder(Order order);
}
