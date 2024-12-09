package com.makeorder.domain.order.validator;

import com.makeorder.api.order.dto.OrderProductDto;
import com.makeorder.exception.CustomException;
import com.makeorder.exception.ErrorCode;
import com.makeorder.product.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class OrderCreateValidator {

    public void validate(Map<Long, OrderProductDto> orderProductMap, List<Long> productIds, List<Product> products) {
        // 제품 정보 확인
        if (products.size() != productIds.size()) {
            throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        // 재고 확인
        products.forEach(product -> {
            if (product.getQuantity() < orderProductMap.get(product.getProductId()).getQuantity()
                    || product.getQuantity() <= 0
            ) {
                throw new CustomException(ErrorCode.PRODUCT_OUT_OF_STOCK);
            }
        });
    }
}
