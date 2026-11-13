package com.practice.freeboard.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 로그아웃 요청 DTO.
 * 클라가 들고 있던 Refresh 문자열을 넘긴다.
 * 서버는 이 값으로 DB에서 해당 토큰 1개만 폐기한다.
 */
@Getter
@AllArgsConstructor
public class LogoutCommand {

    /** 지금 기기에서 쓰던 Refresh. 이 한 줄만 revoked 처리 (로그아웃용) */
    private String refreshToken;
}
