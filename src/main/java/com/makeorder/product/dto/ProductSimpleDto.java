package com.makeorder.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductSimpleDto {
    private Long productId;
    private String categoryName;
    private String name;
    private Integer price;
    private Boolean isFreeDelivery;
    private Integer deliveryFee;
    private BigDecimal score;
    private Long viewCnt;
    private LocalDateTime regDt;
}
