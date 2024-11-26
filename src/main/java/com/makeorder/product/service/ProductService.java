package com.makeorder.product.service;

import com.makeorder.exception.CustomException;
import com.makeorder.exception.ErrorCode;
import com.makeorder.product.dto.ProductDetailsDto;
import com.makeorder.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDetailsDto getProductDetails(Long productId) {
        ProductDetailsDto productDetails = productRepository.getProductDetails(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        productRepository.increaseViewCount(productId);

        return productDetails;
    }
}
