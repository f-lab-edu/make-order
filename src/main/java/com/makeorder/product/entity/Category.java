package com.makeorder.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CATEGORY")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID", updatable = false)
    private Integer categoryId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PARENT_ID")
    private Integer parentId;
}
