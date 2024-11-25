package com.makeorder.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public abstract class BaseRegModDtEntity {

    @CreatedDate
    @Column(name ="REG_DT", updatable = false)
    private LocalDateTime regDt;


    @LastModifiedDate
    @Column(name ="MOD_DT")
    private LocalDateTime modDt;
}