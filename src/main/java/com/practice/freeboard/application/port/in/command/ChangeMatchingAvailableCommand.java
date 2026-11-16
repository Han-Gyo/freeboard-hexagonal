package com.practice.freeboard.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 수의사 상담 가능 ON/OFF 입력.
 * memberId: JWT에서 꺼낸 로그인 회원
 * available: true=ON, false=OFF (라디오 값)
 */
@Getter
@AllArgsConstructor
public class ChangeMatchingAvailableCommand {

    private Long memberId;
    private boolean available;
}
