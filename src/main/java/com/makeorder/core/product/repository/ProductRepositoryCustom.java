package com.makeorder.core.product.repository;

import com.makeorder.product.dto.ProductDetailsDto;
import com.makeorder.product.dto.ProductSearchRequest;
import com.makeorder.product.dto.ProductSimpleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductRepositoryCustom {
    Page<ProductSimpleDto> getProducts(ProductSearchRequest searchRequest, Pageable pageable);

    Optional<ProductDetailsDto> getProductDetails(Long id);

    void increaseViewCount(Long id);
}
