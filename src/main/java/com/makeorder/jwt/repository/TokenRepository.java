package com.makeorder.jwt.repository;

import com.makeorder.jwt.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Token findByMemberId(Long memberId);
}
