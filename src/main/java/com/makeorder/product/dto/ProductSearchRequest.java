package com.makeorder.product.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchRequest {
    Integer category;
    String keyword;
    String freeDelivery;
    Boolean isFreeDelivery;

    @JsonCreator
    public ProductSearchRequest(@JsonProperty("freeDelivery") String freeDelivery) {
        this.isFreeDelivery = "Y".equalsIgnoreCase(freeDelivery);
    }
}
