package com.practice.freeboard.application.port.out;

/**
 * 탈퇴용 출구.
 * UseCase는 JPA를 직접 안 보고, "이 회원 행 지워"만 이 포트로 시킨다.
 * Refresh 폐기는 RefreshTokenPort 몫이라 여기선 회원 테이블만 담당.
 */
public interface WithdrawPort {

    /**
     * member 테이블에서 해당 id 행 삭제.
     * 호출 전에 Refresh는 이미 다 폐기해 두는 쪽이 안전하다.
     */
    void deleteById(Long memberId);
}
