package com.practice.freeboard.application.exception;

/**
 * 로그인 실패 (이메일 없음 또는 비밀번호 불일치).
 * 보안상 "이메일 없음" / "비번 틀림"을 구분해 알려주지 않고 같은 메시지로 통일한다.
 */
public class LoginFailedException extends RuntimeException {

    public LoginFailedException() {
        super("이메일 또는 비밀번호가 올바르지 않습니다.");
    }
}
