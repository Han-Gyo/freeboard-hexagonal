package com.practice.freeboard.application.service;

import com.practice.freeboard.application.port.in.command.WithdrawCommand;
import com.practice.freeboard.application.port.in.usecase.WithdrawUseCase;
import com.practice.freeboard.application.port.out.RefreshTokenPort;
import com.practice.freeboard.application.port.out.WithdrawPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 탈퇴.
 * 1) 그 회원 Refresh 전부 폐기 → 이후 재발급 불가
 * 2) member 행 삭제
 * memberId는 JWT(Access)에서 온 값. Body로 안 받음. 해킹 위험성 있기때문 Body에 해커나 다른 사람이 id값을 넣어서 탈퇴하는걸 막기위함
 */
@Service
@AllArgsConstructor
public class WithdrawService implements WithdrawUseCase {

    private final RefreshTokenPort refreshTokenPort;
    private final WithdrawPort withdrawPort;

    @Override
    @Transactional // 토큰 먼저 끊고 탈퇴를 진행해야함 둘 다 커밋이 완료 되어야 처리가 되게끔 묶어야함. 하나라도 커밋 실패시 롤백.
    public void withdraw(WithdrawCommand command) {
        Long memberId = command.getMemberId();

        // 1) 이 사람 Refresh 전부 revoked=true
        //    (기기 여러 대여도 한 번에 끊김)
        refreshTokenPort.revokeAllByMemberId(memberId);

        // 2) 회원 행 삭제
        withdrawPort.deleteById(memberId);
    }
}
