package com.practice.freeboard.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 탈퇴 요청 DTO.
 * 재료는 memberId 하나.
 * 컨트롤러가 Access(JWT)에서 꺼낸 "지금 로그인한 나"의 id를 담아 넘긴다.
 */
@Getter
@AllArgsConstructor
public class WithdrawCommand {

    /** 탈퇴할 회원 번호. SecurityContext(JWT)에서 온 값 */
    private Long memberId;
}
