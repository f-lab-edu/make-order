package com.makeorder.order.service;

import com.makeorder.common.util.MemberAuthenticationUtil;
import com.makeorder.exception.CustomException;
import com.makeorder.exception.ErrorCode;
import com.makeorder.member.MemberRepository;
import com.makeorder.member.entity.Member;
import com.makeorder.order.dto.OrderPaymentRequest;
import com.makeorder.order.dto.OrderProductDto;
import com.makeorder.order.dto.OrderRequest;
import com.makeorder.order.dto.OrderResponse;
import com.makeorder.order.entity.Order;
import com.makeorder.order.entity.OrderEvent;
import com.makeorder.order.entity.OrderEventPayment;
import com.makeorder.order.entity.OrderItem;
import com.makeorder.order.enums.OrderStatusType;
import com.makeorder.order.repository.OrderEventPaymentRepository;
import com.makeorder.order.repository.OrderEventRepository;
import com.makeorder.order.repository.OrderItemRepository;
import com.makeorder.order.repository.OrderRepository;
import com.makeorder.product.entity.Product;
import com.makeorder.product.entity.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderEventRepository orderEventRepository;
    private final OrderEventPaymentRepository orderEventPaymentRepository;

    public OrderResponse order(OrderRequest orderRequest) {
        // 회원 조회
        Long memberId = MemberAuthenticationUtil.getLoginMemberId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // 상품 조회
        List<OrderProductDto> orderProducts = orderRequest.getOrderProducts();
        List<Long> productIds = orderProducts.stream()
                                            .map(OrderProductDto::getProductId)
                                            .toList();
        List<Product> products = productRepository.findAllById(productIds);

        if (products.size() != productIds.size()) {
            throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        // 재고 확인
        products.forEach(p -> {
            orderProducts.stream()
                    .filter(orderProduct -> Objects.equals(orderProduct.getProductId(), p.getProductId()))
                    .findAny()
                    .ifPresent(orderProduct -> {
                        if (orderProduct.getQuantity() > p.getQuantity()) {
                            throw new CustomException(ErrorCode.PRODUCT_OUT_OF_STOCK);
                        }
                    });
        });

        // 결제 금액 계산
        int totalPrice = 0;
        for (Product p : products) {
            for (OrderProductDto orderProduct : orderProducts) {
                if (Objects.equals(orderProduct.getProductId(), p.getProductId())) {
                    totalPrice += p.getPrice() * orderProduct.getQuantity();
                }
            }
        }
//        AtomicReference<AtomicInteger> totalPrice = new AtomicReference<>(new AtomicInteger());
//        products.forEach(p -> {
//            orderProducts.forEach(orderProduct -> {
//                if (orderProduct.getProductId().equals(p.getProductId())) {
//                    totalPrice.updateAndGet(v -> v + 1);
//                }
//            });
//        });

        // create and save ORDER
        Order order = Order.builder()
                .member(member)
                .rcpntName(orderRequest.getRcpntName())
                .rcpntAddress(orderRequest.getRcpntAddress())
                .rcpntPhone(orderRequest.getRcpntPhone())
                .comments(orderRequest.getComments())
                .totalPrice(totalPrice)
                .build();
        orderRepository.save(order);

        // create and save ORDER_EVENT
        orderEventRepository.save(OrderEvent.builder()
                .order(order)
                .orderStatus(OrderStatusType.ORDER_ACCEPT)
                .build());

        products.forEach(p -> {
            orderProducts.stream()
                    .filter(orderProduct -> Objects.equals(orderProduct.getProductId(), p.getProductId()))
                    .findAny()
                    .ifPresent(orderProduct -> {
                        // 재고 감소
                        p.subtractQuantity(orderProduct.getQuantity());

                        // CREATE AND SAVE ORDER_ITEM
                        orderItemRepository.save(OrderItem.builder()
                                .order(order)
                                .product(p)
                                .productName(p.getName())
                                .productPrice(p.getPrice())
                                .paymentPrice(p.getPrice())
                                .quantity(p.getQuantity())
                                .isFreeDelivery(p.getIsFreeDelivery())
                                .deliveryFee(p.getDeliveryFee())
                                .build());
                    });
        });

        return OrderResponse.of(order);
    }

    public void pay(OrderPaymentRequest orderPaymentRequest) {
        // 회원 조회
        Long memberId = MemberAuthenticationUtil.getLoginMemberId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // 주문 확인
        Order order = orderRepository.findById(orderPaymentRequest.getOrderId())
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        // 결제 완료된 주문인지 확인
        Optional<OrderEvent> orderEventOptional = orderEventRepository.findByOrderAndOrderStatus(order, OrderStatusType.PAYMENT_COMPLETE);
        if (orderEventOptional.isPresent()) {
            throw new CustomException(ErrorCode.PAID_ORDER);
        }

        // 유효하지 않은 사용자 체크
        if (order.getMember() != member) {
            throw new CustomException(ErrorCode.INVALID_ORDER_USER);
        }

        // 주문 금액 체크
        if (!Objects.equals(order.getTotalPrice(), orderPaymentRequest.getPaymentPrice())) {
            orderEventRepository.save(OrderEvent.builder()
                    .order(order)
                    .orderStatus(OrderStatusType.PAYMENT_FAIL)
                    .build());
            throw new CustomException(ErrorCode.PAYMENT_PRICE_INVALID);
        }

        // 주문 이벤트 추가
        OrderEvent orderEvent = OrderEvent.builder()
                .order(order)
                .orderStatus(OrderStatusType.PAYMENT_COMPLETE)
                .build();
        orderEventRepository.save(orderEvent);

        // 주문 결제 추가
        orderEventPaymentRepository.save(OrderEventPayment.builder()
                .orderEvent(orderEvent)
                .paymentMethod(orderPaymentRequest.getPaymentMethod())
                .paymentPrice(orderPaymentRequest.getPaymentPrice())
                .build());
    }
}
