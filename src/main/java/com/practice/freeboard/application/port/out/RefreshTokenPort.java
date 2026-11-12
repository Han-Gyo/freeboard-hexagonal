package com.practice.freeboard.application.port.out;

import com.practice.freeboard.domain.model.RefreshToken;

import java.util.Optional;

/**
 * Refresh 저장·조회 출구.
 * UseCase/발급기는 JPA를 직접 안 보고 이 포트만 씀.
 */
public interface RefreshTokenPort {

    /** 로그인 때(첫 로그인 / 리프레쉬 토큰 끝났을 때 / 오랜만에 로그인할 때) 새 Refresh 저장. 저장 후 id 채운 도메인 반환 */
    RefreshToken save(RefreshToken refreshToken);

    /** 클라이언트가 보낸 문자열로 찾기. /auth/refresh (리프레쉬 토큰 재발급) 때 사용 */
    Optional<RefreshToken> findByToken(String token);

    /**
     * 토큰 폐기 (revoked = true).
     * 재발급 때 옛 Refresh 죽이고 새로 발급할 때 씀 (회전).
     * 로그아웃 때 "지금 쓰는 Refresh 하나" 죽일 때도 씀.
     */
    void revoke(String token);

    /**
     * 이 회원이 가진 Refresh를 전부 폐기.
     * 탈퇴할 때 / 모든 기기에서 끊고 싶을 때 씀.
     * 토큰 문자열을 몰라도 memberId만 있으면 된다.
     */
    void revokeAllByMemberId(Long memberId);
}
