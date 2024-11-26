package com.makeorder.product.repository;

import com.makeorder.product.dto.ProductDetailsDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.makeorder.product.entity.QCategory.category;
import static com.makeorder.product.entity.QProduct.product;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<ProductDetailsDto> getProductDetails(Long id) {
        ProductDetailsDto productDetailsDto = queryFactory
                .select(Projections.fields(ProductDetailsDto.class,
                        product.productId,
                        category.name.as("categoryName"),
                        product.name,
                        product.quantity,
                        product.price,
                        product.isFreeDelivery,
                        product.deliveryFee,
                        product.score,
                        product.viewCnt,
                        product.regDt,
                        product.modDt
                ))
                .from(product)
                .join(product.category, category)
                .where(product.productId.eq(id))
                .fetchOne();
        return Optional.ofNullable(productDetailsDto);
    }

    @Override
    public void increaseViewCount(Long id) {
        queryFactory
                .update(product)
                .set(product.viewCnt, product.viewCnt.add(1))
                .where(product.productId.eq(id))
                .execute();
    }
}