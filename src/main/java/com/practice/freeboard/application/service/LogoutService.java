package com.practice.freeboard.application.service;

import com.practice.freeboard.application.port.in.command.LogoutCommand;
import com.practice.freeboard.application.port.in.usecase.LogoutUseCase;
import com.practice.freeboard.application.port.out.RefreshTokenPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 로그아웃.
 * 클라가 준 Refresh 1개만 DB에서 폐기한다.
 * 이미 없거나 이미 죽은 토큰이어도 그냥 통과 (다시 로그아웃 눌러도 에러 안 냄).
 */
@Service
@AllArgsConstructor
public class LogoutService implements LogoutUseCase {

    private final RefreshTokenPort refreshTokenPort;

    @Override
    public void logout(LogoutCommand command) {
        // 이 기기 Refresh만 죽임. 다른 기기 토큰은 그대로
        refreshTokenPort.revoke(command.getRefreshToken());
    }
}
