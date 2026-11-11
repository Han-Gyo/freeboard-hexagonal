package com.practice.freeboard.application.port.in.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Access 재발급 성공 결과.
 * 실무처럼 Refresh도 같이 갈아끼운다 (회전).
 * 클라는 둘 다 새 값으로 교체해서 보관.
 */
@Getter
@AllArgsConstructor
public class RefreshResult {

    private String accessToken;
    private String refreshToken;
}
