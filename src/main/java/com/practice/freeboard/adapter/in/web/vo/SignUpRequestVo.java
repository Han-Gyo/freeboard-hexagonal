package com.practice.freeboard.adapter.in.web.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원가입 HTTP 요청 Body.
 * @Valid로 "형식"만 입구에서 막는다. (비즈니스 중복 검사는 Service)
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestVo {

    @NotBlank
    @Email // 이메일 형식인지
    private String email;

    @NotBlank
    @Size(min = 8, max = 50) // 너무 짧은 비번 입구에서 거절
    private String password;
}
