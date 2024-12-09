package com.makeorder.core.product.repository;

import com.makeorder.product.dto.ProductDetailsDto;
import com.makeorder.product.dto.ProductSearchRequest;
import com.makeorder.product.dto.ProductSimpleDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;

import static com.makeorder.product.entity.QCategory.category;
import static com.makeorder.product.entity.QProduct.product;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ProductSimpleDto> getProducts(ProductSearchRequest searchRequest, Pageable pageable) {
        List<ProductSimpleDto> products = queryFactory
                .select(Projections.fields(ProductSimpleDto.class,
                        product.productId,
                        category.name.as("categoryName"),
                        product.name,
                        product.price,
                        product.isFreeDelivery,
                        product.deliveryFee,
                        product.score,
                        product.viewCnt,
                        product.regDt))
                .from(product)
                .join(product.category, category)
                .where(categoryEq(searchRequest.getCategory()),
                        keywordContains(searchRequest.getKeyword()),
                        freeDeliveryEq(searchRequest.getFreeDelivery(), searchRequest.getIsFreeDelivery()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<ProductSimpleDto> countQuery = queryFactory
                .select(Projections.fields(ProductSimpleDto.class,
                        product.productId,
                        category.name.as("categoryName"),
                        product.name,
                        product.price,
                        product.isFreeDelivery,
                        product.deliveryFee,
                        product.score,
                        product.viewCnt,
                        product.regDt))
                .from(product)
                .join(product.category, category)
                .where(categoryEq(searchRequest.getCategory()),
                        keywordContains(searchRequest.getKeyword()),
                        freeDeliveryEq(searchRequest.getFreeDelivery(), searchRequest.getIsFreeDelivery()));

        return PageableExecutionUtils.getPage(products, pageable, countQuery::fetchCount);
    }

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

    private BooleanExpression categoryEq(Integer category) {
        return category != null ? product.category.categoryId.eq(category) : null;
    }

    private BooleanExpression keywordContains(String keyword) {
        return hasText(keyword) ? product.name.contains(keyword) : null;
    }

    private BooleanExpression freeDeliveryEq(String freeDelivery, Boolean isFreeDelivery) {
        return hasText(freeDelivery) ? product.isFreeDelivery.eq(isFreeDelivery) : null;
    }
}