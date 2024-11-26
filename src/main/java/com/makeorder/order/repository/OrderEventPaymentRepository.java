package com.makeorder.order.repository;

import com.makeorder.order.entity.OrderEventPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderEventPaymentRepository extends JpaRepository<OrderEventPayment, Long> {
}
