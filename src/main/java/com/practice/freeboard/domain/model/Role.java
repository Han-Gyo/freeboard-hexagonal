package com.practice.freeboard.domain.model;

/**
 * 회원 역할(인가에 쓰는 권한 구분).
 * JWT 안에 이 값을 넣고, SecurityFilterChain이 URL 통과 여부를 판단할 때 본다.
 *
 * 수의사 서비스 기준으로 연습용 최소 세트만 둔다.
 * HOSPITAL_ADMIN 등은 나중에 필요하면 추가하면 된다.
 */
public enum Role {

    /** 일반 보호자(회원) */
    USER,

    /** 수의사 */
    VET,

    /** 관리자 */
    ADMIN
}
