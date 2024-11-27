package com.makeorder.product.service;

import com.makeorder.exception.CustomException;
import com.makeorder.exception.ErrorCode;
import com.makeorder.product.dto.ProductDetailsDto;
import com.makeorder.product.dto.ProductSearchRequest;
import com.makeorder.product.dto.ProductSearchResponse;
import com.makeorder.product.dto.ProductSimpleDto;
import com.makeorder.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductSearchResponse getProducts(ProductSearchRequest productSearchRequest, Pageable pageable) {
        Page<ProductSimpleDto> products = productRepository.getProducts(productSearchRequest, pageable);

        return ProductSearchResponse.of(products);
    }

    public ProductDetailsDto getProductDetails(Long productId) {
        ProductDetailsDto productDetails = productRepository.getProductDetails(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        productRepository.increaseViewCount(productId);

        return productDetails;
    }
}
