package com.practice.freeboard.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 로그인 입력 DTO.
 * VO에서 넘어온 이메일·평문 비번을 UseCase로 운반한다.
 */
@Getter
@AllArgsConstructor
public class LoginCommand {

    /** 가입 때 쓴 이메일 */
    private String email;

    /** 클라이언트가 입력한 평문 비밀번호 */
    private String password;
}
