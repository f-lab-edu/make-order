package com.makeorder.order.entity;

import com.makeorder.common.converter.YNToBooleanConverter;
import com.makeorder.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ORDER_ITEM")
@NoArgsConstructor
@Getter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ITEM_ID", updatable = false)
    private Long orderItemtId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "PRODUCT_PRICE")
    private Integer productPrice;

    @Column(name = "PAYMENT_PRICE")
    private Integer paymentPrice;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "FREE_DELIVERY_YN")
    @Convert(converter = YNToBooleanConverter.class)
    private Boolean isFreeDelivery;

    @Column(name = "DELIVERY_FEE")
    private Integer deliveryFee;
}