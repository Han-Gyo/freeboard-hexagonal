package com.practice.freeboard.application.service;

import com.practice.freeboard.adapter.in.web.security.JwtTokenProvider;
import com.practice.freeboard.adapter.in.web.security.RefreshTokenIssuer;
import com.practice.freeboard.application.exception.InvalidRefreshTokenException;
import com.practice.freeboard.application.port.in.command.RefreshCommand;
import com.practice.freeboard.application.port.in.result.RefreshResult;
import com.practice.freeboard.application.port.in.usecase.RefreshUseCase;
import com.practice.freeboard.application.port.out.LoginPort;
import com.practice.freeboard.application.port.out.RefreshTokenPort;
import com.practice.freeboard.domain.model.Member;
import com.practice.freeboard.domain.model.RefreshToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Access 재발급.
 * 흐름: Refresh 조회 → isUsable → 회원 조회 → 새 Access
 *      → 옛 Refresh 폐기 → 새 Refresh 발급 (회전)
 */
@Service
@AllArgsConstructor
public class RefreshService implements RefreshUseCase {

    private final RefreshTokenPort refreshTokenPort;
    private final LoginPort loginPort;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenIssuer refreshTokenIssuer;

    @Override
    public RefreshResult refresh(RefreshCommand command) {

        // 1) DB에서 Refresh 행 조회 → 도메인으로 받음
        RefreshToken stored = refreshTokenPort.findByToken(command.getRefreshToken())
                .orElseThrow(InvalidRefreshTokenException::new);

        // 2) 도메인 규칙: 폐기·만료면 실패
        if (!stored.isUsable()) {
            throw new InvalidRefreshTokenException();
        }

        // 3) Access에 넣을 role 때문에 회원 조회
        Member member = loginPort.findById(stored.getMemberId())
                .orElseThrow(InvalidRefreshTokenException::new);

        // 4) 새 Access (Bearer에 넣을 값이 여기서 바뀜)
        String newAccessToken = jwtTokenProvider.createAccessToken(
                member.getId(),
                member.getRole()
        );

        // 5) 옛 Refresh 폐기 (한번 쓰인 재발급권 재사용 막기)
        refreshTokenPort.revoke(stored.getToken());

        // 6) 새 Refresh 발급·저장
        String newRefreshToken = refreshTokenIssuer.issue(member.getId());

        return new RefreshResult(newAccessToken, newRefreshToken);
    }
}
