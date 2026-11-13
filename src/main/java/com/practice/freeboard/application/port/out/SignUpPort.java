package com.practice.freeboard.application.port.out;

import com.practice.freeboard.domain.model.Member;

/**
 * 회원가입용 출구(out port).
 * UseCase는 DB/JPA를 직접 모르고, 이 포트로만 "있나?" / "저장" 한다.
 * 실제 MySQL 연동은 SignUpAdapter가 담당.
 */
public interface SignUpPort {

    /**
     * 이미 가입된 이메일인지.
     * true면 중복이라 가입을 막아야 한다.
     */
    boolean existsByEmail(String email);

    /**
     * 회원 저장.
     * 저장 후 DB가 만든 id가 채워진 Member를 돌려받는다.
     */
    Member save(Member member);
}
