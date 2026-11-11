package com.practice.freeboard.application.port.out;

import com.practice.freeboard.domain.model.Member;

import java.util.Optional;

/**
 * 로그인용 DB 조회 출구(out port).
 * 이메일로 회원을 찾아 비번 비교·JWT 발급에 쓴다.
 */
public interface LoginPort {

    /**
     * 이메일로 회원 1명 찾기.
     * 없으면 Optional.empty() → 로그인 실패 처리.
     */
    Optional<Member> findByEmail(String email);

    /**
     * id로 회원 찾기.
     * Refresh 재발급 때 memberId로 role 꺼내 Access 만들 때 씀.
     */
    Optional<Member> findById(Long id);
}
