package com.makeorder.order.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequest {
    private String memberId;
    private String rcpntName;
    private String rcpntAddress;
    private String rcpntPhone;
    private String comments;
    private List<OrderProductDto> orderProducts;
}