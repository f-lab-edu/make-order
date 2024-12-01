package com.makeorder.core.order.repository;

import com.makeorder.core.order.entity.Order;
import com.makeorder.core.order.entity.OrderEvent;
import com.makeorder.core.order.entity.enums.OrderStatusType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderEventRepository extends JpaRepository<OrderEvent, Long> {
    Optional<OrderEvent> findByOrderAndOrderStatus(Order order, OrderStatusType orderStatus);
}
