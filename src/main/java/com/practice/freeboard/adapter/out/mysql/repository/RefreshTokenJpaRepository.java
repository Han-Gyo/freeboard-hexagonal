package com.practice.freeboard.adapter.out.mysql.repository;

import com.practice.freeboard.adapter.out.mysql.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Refresh 토큰 DB 접근.
 * 재발급: 클라이언트가 보낸 문자열로 행 찾기
 * 저장: 로그인 때 새 토큰 insert
 * 탈퇴 등: memberId로 그 사람 토큰들 찾기
 */
public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenEntity, Long> {

    /**
     * Refresh 문자열로 1건 조회.
     * /auth/refresh 들어왔을 때 "DB에 있는 진짜 토큰인지" 볼 때 씀.
     */
    Optional<RefreshTokenEntity> findByToken(String token);

    /**
     * 이 회원(memberId)으로 저장된 Refresh 전부.
     * 탈퇴·전체 로그아웃 때 한 명 거를 모아서 폐기할 때 씀.
     */
    List<RefreshTokenEntity> findByMemberId(Long memberId);
}
