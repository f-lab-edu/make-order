package com.makeorder.core.order.entity;

import com.makeorder.common.entity.BaseRegDtEntity;
import com.makeorder.core.order.converter.OrderStatusTypeConverter;
import com.makeorder.core.order.entity.enums.OrderStatusType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ORDER_EVENT")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class OrderEvent extends BaseRegDtEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_EVENT_ID", updatable = false)
    private Long orderEventId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @Column(name = "STATUS_CD")
    @Convert(converter = OrderStatusTypeConverter.class)
    private OrderStatusType orderStatus;
}