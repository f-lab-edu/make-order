package com.makeorder.domain.order.service;

import com.makeorder.api.order.dto.OrderProductDto;
import com.makeorder.api.order.dto.OrderRequest;
import com.makeorder.api.order.dto.OrderResponse;
import com.makeorder.common.util.MemberAuthenticationUtil;
import com.makeorder.core.member.entity.Member;
import com.makeorder.core.member.service.MemberFindService;
import com.makeorder.core.order.entity.Order;
import com.makeorder.core.order.entity.enums.OrderStatusType;
import com.makeorder.core.order.service.OrderCommandService;
import com.makeorder.core.order.service.OrderEventCommandService;
import com.makeorder.core.order.service.OrderItemCommandService;
import com.makeorder.core.product.service.ProductFindService;
import com.makeorder.domain.order.validator.OrderCreateValidator;
import com.makeorder.product.entity.Product;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final MemberFindService memberFindService;
    private final ProductFindService productFindService;
    private final OrderCommandService orderCommandService;
    private final OrderEventCommandService orderEventCommandService;
    private final OrderItemCommandService orderItemCommandService;
    private final OrderCreateValidator orderCreateValidator;

    public OrderResponse order(OrderRequest orderRequest) {
        // 회원 조회
        Optional<Member> memberOptional = memberFindService.findMember(MemberAuthenticationUtil.getLoginMemberId());

        // 상품 조회
        List<OrderProductDto> orderProducts = orderRequest.getOrderProducts();
        Map<Long, OrderProductDto> orderProductMap = orderProducts.stream()
                .collect(Collectors.toMap(OrderProductDto::getProductId, Function.identity()));
        List<Long> productIds = orderProducts.stream()
                .map(OrderProductDto::getProductId)
                .toList();
        List<Product> products = productFindService.findAllById(productIds);

        // 검증
        orderCreateValidator.validate(memberOptional, orderProductMap, productIds, products);

        // 결제 금액 계산
        int totalPrice = getTotalPrice(products, orderProducts);

        // 주문 저장
        Member member = memberOptional.get();
        Order order = orderCommandService.createOrder(orderRequest, member, totalPrice);

        // 주문 접수 이벤트 저장
        orderEventCommandService.createOrderEvent(order, OrderStatusType.ORDER_ACCEPT);

        // 주문 아이템 저장
        products.forEach(p -> orderItemCommandService.createOrderItem(p, order, orderProductMap.get(p.getProductId()).getQuantity()));

        return OrderResponse.of(order);
    }

    private int getTotalPrice(List<Product> products, List<OrderProductDto> orderProducts) {
        Map<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getProductId, Function.identity()));

        return orderProducts.stream()
                .mapToInt(p -> productMap.get(p.getProductId()).getPrice() * p.getQuantity())
                .sum();
    }
}
