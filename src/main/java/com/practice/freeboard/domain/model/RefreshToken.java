package com.practice.freeboard.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Refresh 토큰 도메인.
 * DB 행이 아니라 "우리 서비스에서 말하는 재발급권" 개념.
 * 로그아웃·재발급 전에 이 객체가 아직 쓸 수 있는지 본다.
 */
@Getter
@AllArgsConstructor
public class RefreshToken {

    private Long id;
    private String token;
    private Long memberId;
    private LocalDateTime expiresAt;
    private boolean revoked;
    private LocalDateTime createdAt;

    /** 로그아웃 등으로 폐기. 만료 전이라도 못 쓰게 */
    public void revoke() {

        this.revoked = true;
    }

    /** 폐기 아니고 만료 전인지 */
    public boolean isUsable() {

        return !revoked && expiresAt.isAfter(LocalDateTime.now());
    }
}
