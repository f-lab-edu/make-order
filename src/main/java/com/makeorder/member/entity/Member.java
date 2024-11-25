package com.makeorder.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MEMBER")
@NoArgsConstructor
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
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
