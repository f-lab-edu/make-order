package com.makeorder.order.repository;

import com.makeorder.order.entity.Order;
import com.makeorder.order.entity.OrderEvent;
import com.makeorder.order.enums.OrderStatusType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderEventRepository extends JpaRepository<OrderEvent, Long> {
    Optional<OrderEvent> findByOrderAndOrderStatus(Order order, OrderStatusType orderStatus);
}
