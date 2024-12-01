package com.makeorder.core.order.entity;

import com.makeorder.common.entity.BaseRegModDtEntity;
import com.makeorder.core.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ORDERS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Order extends BaseRegModDtEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID", updatable = false)
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(name = "RCPNT_NAME")
    private String rcpntName;

    @Column(name = "RCPNT_ADDRESS")
    private String rcpntAddress;

    @Column(name = "RCPNT_PHONE")
    private String rcpntPhone;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "TOTAL_PRICE")
    private Integer totalPrice;
}
