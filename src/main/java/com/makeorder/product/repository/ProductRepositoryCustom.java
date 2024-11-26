package com.makeorder.product.repository;

import com.makeorder.product.dto.ProductDetailsDto;

import java.util.Optional;

public interface ProductRepositoryCustom {
    Optional<ProductDetailsDto> getProductDetails(Long id);
    void increaseViewCount(Long id);
}
