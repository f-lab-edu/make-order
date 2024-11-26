package com.makeorder.product.entity;

import com.makeorder.common.converter.YNToBooleanConverter;
import com.makeorder.common.entity.BaseRegModDtEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCT")
@SQLDelete(sql = "UPDATE PRODUCT SET DEL_YN = 'Y' WHERE PRODUCR_ID = ?")
@SQLRestriction("DEL_YN = 'N'")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Product extends BaseRegModDtEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID", updatable = false)
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @Column(name = "NAME")
    private String name;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "PRICE")
    private Integer price;

    @Column(name = "FREE_DELIVERY_YN")
    @Convert(converter = YNToBooleanConverter.class)
    private Boolean isFreeDelivery;

    @Column(name = "DELIVERY_FEE")
    private Integer deliveryFee;

    @Column(name = "SCORE")
    private BigDecimal score;

    @Column(name = "VIEW_CNT")
    private Long viewCnt;

    @Column(name = "DEL_YN")
    @Convert(converter = YNToBooleanConverter.class)
    private Boolean isDel;

    public void subtractQuantity(Integer quantity) {
        this.quantity -= quantity;
    }
}