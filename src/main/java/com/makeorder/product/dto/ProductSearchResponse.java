package com.makeorder.product.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class ProductSearchResponse {
    List<ProductSimpleDto> products;
    int totalPageSize;
    int pageSize;
    int pageNumber;
    long totalCount;

    public static ProductSearchResponse of(Page<ProductSimpleDto> products) {
        ProductSearchResponse response = new ProductSearchResponse();
        response.setProducts(products.getContent());
        response.setTotalPageSize(products.getTotalPages());
        response.setPageSize(products.getSize());
        response.setPageNumber(products.getNumber());
        response.setTotalCount(products.getTotalElements());
        return response;
    }
}