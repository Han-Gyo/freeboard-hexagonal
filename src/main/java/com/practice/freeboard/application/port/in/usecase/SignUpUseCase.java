package com.practice.freeboard.application.port.in.usecase;

import com.practice.freeboard.application.port.in.command.SignUpCommand;

/**
 * 회원가입 유스케이스 입구(in port).
 * 웹 어댑터는 이 인터페이스만 보고 호출한다.
 * 구현체(SignUpService)가 바뀌어도 컨트롤러는 그대로 둘 수 있다.
 */
public interface SignUpUseCase {

    /**
     * 회원 저장 후 발급된 member id 반환.
     * 프론트가 "가입 완료된 회원 번호"를 받을 수 있게 Long으로 돌려준다.
     */
    Long signUp(SignUpCommand command);
}
