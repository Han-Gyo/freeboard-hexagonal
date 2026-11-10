package com.practice.freeboard.application.port.in.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 로그인 성공 결과.
 * 컨트롤러가 JSON으로 내려줄 액세스 토큰을 담는다.
 * (세션 대신 클라이언트가 들고 다닐 사원증)
 */
@Getter
@AllArgsConstructor
public class LoginResult {

    /** Authorization: Bearer 에 넣을 JWT 문자열 */
    private String accessToken;
}
