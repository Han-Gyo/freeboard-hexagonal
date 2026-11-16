package com.practice.freeboard.adapter.out.mysql.repository;

import com.practice.freeboard.adapter.out.mysql.entity.MemberEntity;
import com.practice.freeboard.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    /**
     * 매칭 요청 입구용.
     * role=VET 이고 matchingAvailable=true 인 회원이 한 명이라도 있으면 true.
     * Spring Data 메서드 이름 → COUNT/EXISTS 쿼리 자동 생성.
     */
    boolean existsByRoleAndMatchingAvailable(Role role, boolean matchingAvailable);

    /**
     * 상담 가능 ON/OFF만 갱신.
     * @return 영향 행 수 (1=성공, 0=없는 id)
     */
    @Modifying(clearAutomatically = true)
    @Query("""
            update MemberEntity m
               set m.matchingAvailable = :available
             where m.id = :id
            """)
    int updateMatchingAvailable(
            @Param("id") Long id,
            @Param("available") boolean available
    );
}


