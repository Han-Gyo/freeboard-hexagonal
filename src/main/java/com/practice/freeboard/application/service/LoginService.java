package com.practice.freeboard.application.service;

import com.practice.freeboard.adapter.in.web.security.JwtTokenProvider;
import com.practice.freeboard.adapter.in.web.security.RefreshTokenIssuer;
import com.practice.freeboard.application.exception.LoginFailedException;
import com.practice.freeboard.application.port.in.command.LoginCommand;
import com.practice.freeboard.application.port.in.result.LoginResult;
import com.practice.freeboard.application.port.in.usecase.LoginUseCase;
import com.practice.freeboard.application.port.out.LoginPort;
import com.practice.freeboard.domain.model.Member;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 로그인 유스케이스.
 * 비번 맞으면 Access(JWT) + Refresh(랜덤·DB) 둘 다 발급.
 */
@Service
@AllArgsConstructor
public class LoginService implements LoginUseCase {

    private final LoginPort loginPort;
    private final PasswordEncoder passwordEncoder;

    /** Access JWT 발급 */
    private final JwtTokenProvider jwtTokenProvider;

    /** Refresh 발급 + DB 저장 */
    private final RefreshTokenIssuer refreshTokenIssuer;

    @Override
    public LoginResult login(LoginCommand command) {

        // 1) 이메일로 회원 찾기
        Member member = loginPort.findByEmail(command.getEmail())
                .orElseThrow(LoginFailedException::new);

        // 2) 비번 비교
        if (!passwordEncoder.matches(command.getPassword(), member.getPassword())) {
            throw new LoginFailedException();
        }

        // 3) Access — API용 짧은 사원증
        String accessToken = jwtTokenProvider.createAccessToken(
                member.getId(),
                member.getRole()
        );

        // 4) Refresh — 재로그인 없이 Access 다시 받기 위한 재발급권 (DB 저장됨)
        String refreshToken = refreshTokenIssuer.issue(member.getId());

        return new LoginResult(accessToken, refreshToken);
    }
}
