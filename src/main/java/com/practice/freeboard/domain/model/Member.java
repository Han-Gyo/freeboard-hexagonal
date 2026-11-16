package com.practice.freeboard.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 회원 도메인 모델.
 * DB 테이블이 아니라 "우리 서비스에서 말하는 회원" 개념.
 * 로그인·JWT 발급 때 "누구인지 / 역할이 뭔지" 판단하려면 이 정보가 필요하다.
 *
 * matchingAvailable 은 매칭 요청 한 건의 상태(MatchStatus)랑 다르다.
 * → 수의사 라디오: 지금 상담(매칭) 받을지 ON/OFF
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

    /**
     * 실시간 상담(매칭) 가능 여부.
     * true  = ON → 요청 받을 수 있음
     * false = OFF → 요청 안 받음
     * 가입 직후·보호자는 보통 false.
     */
    private boolean matchingAvailable;

    /** 가입 시각 */
    private LocalDateTime createdAt;

    /**
     * 상담 가능 ON.
     * 라디오를 "가능"으로 켤 때 도메인에서 호출.
     */
    public void turnOnMatching() {
        this.matchingAvailable = true;
    }

    /**
     * 상담 가능 OFF.
     * 라디오를 "불가"로 끌 때 도메인에서 호출.
     */
    public void turnOffMatching() {
        this.matchingAvailable = false;
    }
}
