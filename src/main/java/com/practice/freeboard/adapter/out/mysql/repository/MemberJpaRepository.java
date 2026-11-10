package com.practice.freeboard.adapter.out.mysql.repository;

import com.practice.freeboard.adapter.out.mysql.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 회원 DB 접근.
 * 로그인: 이메일로 1명 찾기
 * 회원가입: 이메일 중복 여부 확인
 * JpaRepository가 save/findById 등은 기본 제공.
 */
public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {

    /**
     * 로그인·중복체크용.
     * Optional: 없을 수도 있으니 null 대신 비어 있는 결과로 받음
     */
    Optional<MemberEntity> findByEmail(String email);

    /**
     * 회원가입 전 중복 이메일인지 빠르게 확인.
     * true면 이미 가입된 이메일.
     */
    boolean existsByEmail(String email);
}

