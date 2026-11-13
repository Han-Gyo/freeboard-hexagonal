package com.practice.freeboard.adapter.in.web.security;

import com.practice.freeboard.application.port.out.RefreshTokenPort;
import com.practice.freeboard.domain.model.RefreshToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Refresh 발급기.
 * Access(JWT)와 다르게 Refresh는 랜덤 문자열 + DB 저장.
 * (폐기하려면 서버에 기록이 있어야 해서)
 */
@Component
public class RefreshTokenIssuer {

    private final RefreshTokenPort refreshTokenPort;

    /** yml jwt.refresh-token-validity-ms */
    private final long refreshTokenValidityMs;

    public RefreshTokenIssuer(
            RefreshTokenPort refreshTokenPort,
            @Value("${jwt.refresh-token-validity-ms}") long refreshTokenValidityMs
    ) {
        this.refreshTokenPort = refreshTokenPort;
        this.refreshTokenValidityMs = refreshTokenValidityMs;
    }

    /**
     * 회원용 Refresh 하나 만들고 DB에 넣은 뒤, 클라에 줄 문자열 반환.
     * 로그인 성공 직후 호출할 예정. (다음 단계에서 LoginService에 연결)
     */
    public String issue(Long memberId) {
        // 추측 어렵게 랜덤 문자열 (UUID 두 개 이어 붙임)
        String token = UUID.randomUUID().toString().replace("-", "")
                + UUID.randomUUID().toString().replace("-", "");

        LocalDateTime now = LocalDateTime.now();
        // yml 수명(ms) → 초로 바꿔 만료 시각 계산
        LocalDateTime expiresAt = now.plusSeconds(refreshTokenValidityMs / 1000);

        RefreshToken refreshToken = new RefreshToken(
                null,
                token,
                memberId,
                expiresAt,
                false,
                now
        );

        refreshTokenPort.save(refreshToken);
        return token;
    }
}
