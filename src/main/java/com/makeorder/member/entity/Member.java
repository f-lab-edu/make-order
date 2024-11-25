package com.makeorder.member.entity;

import com.makeorder.common.entity.BaseRegModDtEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MEMBER")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Member extends BaseRegModDtEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID", updatable = false)
    private Long memberId;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWD")
    private String password;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONE")
    private String phone;
}
