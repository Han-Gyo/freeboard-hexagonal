package com.practice.freeboard.adapter.in.web.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 로그인 HTTP Body.
 * 형식만 @Valid로 막고, 실제 계정 일치 여부는 LoginService가 판단한다.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestVo {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
