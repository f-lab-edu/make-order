package com.makeorder.product.controller;

import com.makeorder.common.dto.DataResponse;
import com.makeorder.product.dto.ProductDetailsDto;
import com.makeorder.product.dto.ProductSearchRequest;
import com.makeorder.product.dto.ProductSearchResponse;
import com.makeorder.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<DataResponse> getProducts(@ModelAttribute ProductSearchRequest productSearchRequest, Pageable pageable) {
        ProductSearchResponse productSearchResponse = productService.getProducts(productSearchRequest, pageable);
        return ResponseEntity.ok(DataResponse.of(200, "Success", productSearchResponse));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<DataResponse> getProductDetails(@PathVariable Long productId) {
        ProductDetailsDto productDetails = productService.getProductDetails(productId);

        return ResponseEntity.ok(DataResponse.of(200, "Success", productDetails));
    }
}
