package com.practice.freeboard.application.port.in.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 로그인 성공 결과.
 * Access: API 호출용 (Bearer)
 * Refresh: Access 만료 후 재발급용 (DB에도 저장됨)
 */
@Getter
@AllArgsConstructor
public class LoginResult {

    /** Authorization: Bearer 에 넣을 JWT */
    private String accessToken;

    /** 클라가 따로 보관. Access 떨어지면 /auth/refresh 때 보냄 */
    private String refreshToken;
}
