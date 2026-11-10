package com.practice.freeboard.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 회원 도메인 모델.
 * DB 테이블이 아니라 "우리 서비스에서 말하는 회원" 개념.
 * 로그인·JWT 발급 때 "누구인지 / 역할이 뭔지" 판단하려면 이 정보가 필요하다.
 */
@Getter
@AllArgsConstructor
public class Member {

    /** 회원 PK. JWT subject 등에 넣을 식별자 */
    private Long id;

    /** 로그인 아이디로 쓸 이메일 */
    private String email;

    /**
     * 비밀번호.
     * 도메인에 들어올 때는 이미 BCrypt 해시된 문자열이어야 한다.
     * 평문 비번을 여기에 오래 들고 있지 않는다.
     */
    private String password;

    /** 인가에 쓰는 역할 (USER / VET / ADMIN) */
    private Role role;

    /** 가입 시각 */
    private LocalDateTime createdAt;
}
