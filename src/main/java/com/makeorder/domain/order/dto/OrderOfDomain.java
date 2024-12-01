package com.makeorder.domain.order.dto;

import com.makeorder.core.order.entity.Order;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderOfDomain {
    private Long orderId;
    private Long memberId;
    private String rcpntName;
    private String rcpntAddress;
    private String rcpntPhone;
    private String comments;
    private Integer totalPrice;

    public static OrderOfDomain of(Order order) {
        OrderOfDomain dto = new OrderOfDomain();
        dto.setOrderId(order.getOrderId());
        dto.setMemberId(order.getMember().getMemberId());
        dto.setRcpntName(order.getRcpntName());
        dto.setRcpntAddress(order.getRcpntAddress());
        dto.setRcpntPhone(order.getRcpntPhone());
        dto.setComments(order.getComments());
        dto.setTotalPrice(order.getTotalPrice());
        return dto;
    }
}
