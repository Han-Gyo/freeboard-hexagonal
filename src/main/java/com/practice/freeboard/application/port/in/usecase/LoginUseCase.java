package com.practice.freeboard.application.port.in.usecase;

import com.practice.freeboard.application.port.in.command.LoginCommand;
import com.practice.freeboard.application.port.in.result.LoginResult;

/**
 * 로그인 유스케이스 입구(in port).
 * 이메일/비번이 맞으면 액세스 토큰을 발급해 돌려준다.
 */
public interface LoginUseCase {

    LoginResult login(LoginCommand command);
}
