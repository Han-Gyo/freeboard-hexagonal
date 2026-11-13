package com.practice.freeboard.adapter.in.web.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Access 재발급 HTTP Body.
 * 로그인 때 받은 refreshToken을 그대로 넣는다.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshRequestVo {

    @NotBlank
    private String refreshToken;
}
