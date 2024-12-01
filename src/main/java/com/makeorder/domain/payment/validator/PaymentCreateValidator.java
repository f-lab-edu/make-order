package com.makeorder.domain.payment.validator;

import com.makeorder.core.member.entity.Member;
import com.makeorder.core.order.entity.Order;
import com.makeorder.core.order.entity.OrderEvent;
import com.makeorder.core.order.entity.OrderItem;
import com.makeorder.core.order.entity.enums.OrderStatusType;
import com.makeorder.core.order.service.OrderEventCommandService;
import com.makeorder.core.order.service.OrderEventFindService;
import com.makeorder.exception.CustomException;
import com.makeorder.exception.ErrorCode;
import com.makeorder.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PaymentCreateValidator {

    private final OrderEventCommandService orderEventCommandService;
    private final OrderEventFindService orderEventFindService;

    public void validate(Optional<Member> memberOptional, Optional<Order> orderOptional, List<OrderItem> orderItems, List<Product> products, Integer paymentPrice) {
        Member member = memberOptional.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        Order order = orderOptional.orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        // 결제 완료된 주문인지 확인
        Optional<OrderEvent> orderEventOptional = orderEventFindService.findOrderEventByOrderAndOrderStatus(order, OrderStatusType.PAYMENT_COMPLETE);
        if (orderEventOptional.isPresent()) {
            throw new CustomException(ErrorCode.PAID_ORDER);
        }

        // 유효하지 않은 사용자 체크 (주문한 사용자와 다른 경우)
        if (order.getMember() != member) {
            throw new CustomException(ErrorCode.INVALID_ORDER_USER);
        }

        // 주문 금액 체크
        if (!Objects.equals(order.getTotalPrice(), paymentPrice)) {
            orderEventCommandService.createOrderEvent(order, OrderStatusType.PAYMENT_FAIL);
            throw new CustomException(ErrorCode.PAYMENT_PRICE_INVALID);
        }

        // 재고 확인
        Map<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductId, Function.identity()));
        orderItems.forEach(orderItem -> {
            if (orderItem.getQuantity() > productMap.get(orderItem.getProduct().getProductId()).getQuantity()
                    || productMap.get(orderItem.getProduct().getProductId()).getQuantity() <= 0) {
                throw new CustomException(ErrorCode.PRODUCT_OUT_OF_STOCK);
            }
        });
    }
}
