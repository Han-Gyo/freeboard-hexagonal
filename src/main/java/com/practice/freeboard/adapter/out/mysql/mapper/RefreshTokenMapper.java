package com.practice.freeboard.adapter.out.mysql.mapper;

import com.practice.freeboard.adapter.out.mysql.entity.RefreshTokenEntity;
import com.practice.freeboard.domain.model.RefreshToken;

/**
 * Refresh Entity <-> 도메인 변환.
 * 어댑터 밖으로 Entity 안 내보내려고 둠.
 */
public final class RefreshTokenMapper {

    private RefreshTokenMapper() {
    }

    /** 신규 저장용. id 없는 Entity */
    public static RefreshTokenEntity toEntity(RefreshToken refreshToken) {
        return new RefreshTokenEntity(
                refreshToken.getToken(),
                refreshToken.getMemberId(),
                refreshToken.getExpiresAt(),
                refreshToken.getCreatedAt()
        );
    }

    /** DB 행 -> 도메인 */
    public static RefreshToken toDomain(RefreshTokenEntity entity) {
        return new RefreshToken(
                entity.getId(),
                entity.getToken(),
                entity.getMemberId(),
                entity.getExpiresAt(),
                entity.isRevoked(),
                entity.getCreatedAt()
        );
    }
}
