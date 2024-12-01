package com.makeorder.core.member.repository;

import com.makeorder.core.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsMemberByEmail(String email);
    Optional<Member> findByEmail(String email);
}
