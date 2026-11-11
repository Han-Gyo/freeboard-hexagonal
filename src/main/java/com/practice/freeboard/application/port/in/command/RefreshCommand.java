package com.practice.freeboard.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Access 재발급 요청 DTO.
 * 클라가 보관 중이던 Refresh 문자열을 담아 UseCase로 넘긴다.
 */
@Getter
@AllArgsConstructor
public class RefreshCommand {

    private String refreshToken;
}
