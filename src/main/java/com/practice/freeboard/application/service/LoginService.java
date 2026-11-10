package com.practice.freeboard.application.service;

import com.practice.freeboard.adapter.in.web.security.JwtTokenProvider;
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
 * 로그인 유스케이스 구현.
 * 흐름: 회원 조회 → 비번 matches → JWT 발급.
 * 왜 Service냐면 "인증 성공 시에만 토큰 준다"가 애플리케이션 규칙이기 때문.
 *
 * 참고: JwtTokenProvider는 인프라(jjwt)라 이상적 헥사에선 out port로 감싸기도 한다.
 * 연습 단계에선 PasswordEncoder처럼 바로 주입해서 흐름을 먼저 익힌다.
 */
@Service
@AllArgsConstructor
public class LoginService implements LoginUseCase {

    /** 이메일로 회원 찾는 출구 */
    private final LoginPort loginPort;

    /** 평문 비번 vs DB 해시 비교 */
    private final PasswordEncoder passwordEncoder;

    /** 액세스 토큰 발급기 */
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public LoginResult login(LoginCommand command) {

        // 1) 이메일로 회원 찾기. 없으면 실패 (존재하는지도 밖에서 모름)
        Member member = loginPort.findByEmail(command.getEmail())
                .orElseThrow(LoginFailedException::new);

        // 2) 입력 평문 vs 저장된 BCrypt 해시 비교. 틀리면 실패
        if (!passwordEncoder.matches(command.getPassword(), member.getPassword())) {
            throw new LoginFailedException();
        }

        // 3) 인증 성공 → id/role 담아 JWT 발급 (클라이언트가 들고 다닐 사원증)
        String accessToken = jwtTokenProvider.createAccessToken(
                member.getId(),
                member.getRole()
        );

        return new LoginResult(accessToken);
    }
}
