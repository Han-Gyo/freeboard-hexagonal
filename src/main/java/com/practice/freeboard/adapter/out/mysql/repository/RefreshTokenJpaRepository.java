package com.practice.freeboard.adapter.out.mysql.repository;

import com.practice.freeboard.adapter.out.mysql.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Refresh 토큰 DB 접근.
 * 재발급: 클라이언트가 보낸 문자열로 행 찾기
 * 저장: 로그인 때 새 토큰 insert
 */
public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenEntity, Long> {

    /**
     * Refresh 문자열로 1건 조회.
     * /auth/refresh 들어왔을 때 "DB에 있는 진짜 토큰인지" 볼 때 씀.
     */
    Optional<RefreshTokenEntity> findByToken(String token);
}
