package com.makeorder.review.entity;

import com.makeorder.common.converter.YNToBooleanConverter;
import com.makeorder.common.entity.BaseRegModDtEntity;
import com.makeorder.member.entity.Member;
import com.makeorder.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "REVIEW")
@SQLDelete(sql = "UPDATE REVIEW SET DEL_YN = 'Y' WHERE REVIEW_ID = ?")
@SQLRestriction("DEL_YN = 'N'")
@NoArgsConstructor
@Getter
public class Review extends BaseRegModDtEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_ID", updatable = false)
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Column(name = "SCORE")
    private Integer score;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "DEL_YN")
    @Convert(converter = YNToBooleanConverter.class)
    private Boolean isDel;
}
