package com.practice.freeboard.adapter.in.web.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 로그아웃 HTTP Body.
 * 로그인 때 받은 refreshToken을 넣으면 그 토큰만 폐기한다.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LogoutRequestVo {

    @NotBlank
    private String refreshToken;
}
