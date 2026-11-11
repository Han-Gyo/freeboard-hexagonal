package com.practice.freeboard.adapter.out.mysql;

import com.practice.freeboard.adapter.out.mysql.entity.RefreshTokenEntity;
import com.practice.freeboard.adapter.out.mysql.mapper.RefreshTokenMapper;
import com.practice.freeboard.adapter.out.mysql.repository.RefreshTokenJpaRepository;
import com.practice.freeboard.application.port.out.RefreshTokenPort;
import com.practice.freeboard.domain.model.RefreshToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * RefreshTokenPort MySQL 구현.
 * 저장·조회만 담당. 문자열 생성은 Issuer가 함.
 */
@Component
@AllArgsConstructor
public class RefreshTokenAdapter implements RefreshTokenPort {

    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        RefreshTokenEntity saved =
                refreshTokenJpaRepository.save(RefreshTokenMapper.toEntity(refreshToken));
        return RefreshTokenMapper.toDomain(saved);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenJpaRepository.findByToken(token)
                .map(RefreshTokenMapper::toDomain);
    }

    @Override
    public void revoke(String token) {
        // Entity를 직접 꺼내 revoke() 후 저장. 도메인만 바꾸면 DB에 안 남음
        refreshTokenJpaRepository.findByToken(token).ifPresent(entity -> {
            entity.revoke();
            refreshTokenJpaRepository.save(entity);
        });
    }
}
