package com.makeorder.product.controller;

import com.makeorder.common.dto.DataResponse;
import com.makeorder.product.dto.ProductDetailsDto;
import com.makeorder.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    public ResponseEntity<DataResponse> getProductDetails(@PathVariable Long productId) {
        ProductDetailsDto productDetails = productService.getProductDetails(productId);

        return ResponseEntity.ok(DataResponse.of(200, "Success", productDetails));
    }
}
