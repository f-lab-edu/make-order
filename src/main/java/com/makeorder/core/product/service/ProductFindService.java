package com.makeorder.core.product.service;

import com.makeorder.core.product.repository.ProductRepository;
import com.makeorder.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductFindService {

    private final ProductRepository productRepository;

    public List<Product> findAllById(List<Long> productIds) {
        return productRepository.findAllById(productIds);
    }
}
