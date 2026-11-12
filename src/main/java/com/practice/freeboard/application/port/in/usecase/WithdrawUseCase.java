package com.practice.freeboard.application.port.in.usecase;

import com.practice.freeboard.application.port.in.command.WithdrawCommand;

/**
 * 탈퇴 유스케이스.
 * 그 회원 Refresh 전부 폐기 + member 행 삭제.
 * 비회원은 못 함 → HTTP는 authenticated (Access 필요). permitAll 아님.
 */
public interface WithdrawUseCase {

    void withdraw(WithdrawCommand command);
}
