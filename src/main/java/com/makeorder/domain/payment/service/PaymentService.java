package com.makeorder.domain.payment.service;

import com.makeorder.api.payment.dto.PaymentRequest;
import com.makeorder.common.util.MemberAuthenticationUtil;
import com.makeorder.core.member.entity.Member;
import com.makeorder.core.member.service.MemberFindService;
import com.makeorder.core.order.entity.Order;
import com.makeorder.core.order.entity.OrderEvent;
import com.makeorder.core.order.entity.OrderItem;
import com.makeorder.core.order.entity.enums.OrderStatusType;
import com.makeorder.core.order.service.OrderEventCommandService;
import com.makeorder.core.order.service.OrderEventFindService;
import com.makeorder.core.order.service.OrderFindService;
import com.makeorder.core.order.service.OrderItemFindService;
import com.makeorder.core.payment.service.PaymentCommandService;
import com.makeorder.core.product.service.ProductFindService;
import com.makeorder.domain.payment.validator.PaymentCreateValidator;
import com.makeorder.product.entity.Product;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentCommandService paymentCommandService;
    private final MemberFindService memberFindService;
    private final OrderFindService orderFindService;
    private final OrderEventCommandService orderEventCommandService;
    private final OrderItemFindService orderItemFindService;
    private final ProductFindService productFindService;
    private final PaymentCreateValidator paymentValidator;

    public void pay(PaymentRequest paymentRequest) {
        // 회원 조회
        Optional<Member> memberOptional = memberFindService.findMember(MemberAuthenticationUtil.getLoginMemberId());

        // 주문 확인
        Optional<Order> orderOptional = orderFindService.findOrder(paymentRequest.getOrderId());

        // 검증
        Order order = orderOptional.get();
        List<OrderItem> orderItems = orderItemFindService.findOrderItemByOrder(order);
        Map<Long, OrderItem> orderItemMap = orderItems.stream()
                .collect(Collectors.toMap(item -> item.getProduct().getProductId(), Function.identity()));
        List<Long> productIds = orderItems.stream()
                .map(item -> item.getProduct().getProductId())
                .toList();
        List<Product> products = productFindService.findAllById(productIds);
        paymentValidator.validate(memberOptional, orderOptional, orderItems, products, paymentRequest.getPaymentPrice());

        // 재고 감소
        products.forEach(product -> product.subtractQuantity(orderItemMap.get(product.getProductId()).getQuantity()));

        // 주문 이벤트 추가
        OrderEvent orderEvent = orderEventCommandService.createOrderEvent(order, OrderStatusType.PAYMENT_COMPLETE);

        // 주문 결제 추가
        paymentCommandService.createPayment(paymentRequest, orderEvent);
    }
}
