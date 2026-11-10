package com.practice.freeboard.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 회원가입 입력 DTO (애플리케이션 계층).
 * 웹 VO에서 한번 걸러진 값이 UseCase로 들어올 때 담는 상자.
 * 컨트롤러/JPA 타입에 의존하지 않게 하려고 따로 둔다.
 */
@Getter
@AllArgsConstructor
public class SignUpCommand {

    /** 로그인에 쓸 이메일 */
    private String email;

    /**
     * 클라이언트가 보낸 평문 비밀번호.
     * 이 값 그대로 DB에 넣으면 안 되고, Service에서 BCrypt encode 한다.
     */
    private String password;
}
