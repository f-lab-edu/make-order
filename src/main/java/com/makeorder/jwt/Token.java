package com.makeorder.jwt;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private String refreshToken;

    public Token(Long memberId, String refreshToken) {
        this.memberId = memberId;
        this.refreshToken = refreshToken;
    }

//    private String expiration;

    public void update(String refreshToken) {
        this.refreshToken = refreshToken;
//        setExpiration();
    }

//    public void setExpiration() {
//        Date date = new Date(System.currentTimeMillis() + JwtUtil.REFRESH_TOKEN_EXPIRATION_MS);
//        this.expiration = date.toString();
//    }
}
