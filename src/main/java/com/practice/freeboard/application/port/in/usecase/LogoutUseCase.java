package com.practice.freeboard.application.port.in.usecase;

import com.practice.freeboard.application.port.in.command.LogoutCommand;

/**
 * 로그아웃 유스케이스.
 * 넘어온 Refresh 1개를 폐기해서, 이후 /auth/refresh 못 하게 한다.
 * (Access는 JWT라 당장 회수 안 됨. 만료될 때까지는 남을 수 있음)
 */
public interface LogoutUseCase {

    void logout(LogoutCommand command);
}
