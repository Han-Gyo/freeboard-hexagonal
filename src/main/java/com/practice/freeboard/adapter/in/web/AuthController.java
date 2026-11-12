package com.practice.freeboard.adapter.in.web;

import com.practice.freeboard.adapter.in.web.vo.LoginRequestVo;
import com.practice.freeboard.adapter.in.web.vo.LogoutRequestVo;
import com.practice.freeboard.adapter.in.web.vo.RefreshRequestVo;
import com.practice.freeboard.adapter.in.web.vo.SignUpRequestVo;
import com.practice.freeboard.application.port.in.command.LoginCommand;
import com.practice.freeboard.application.port.in.command.LogoutCommand;
import com.practice.freeboard.application.port.in.command.RefreshCommand;
import com.practice.freeboard.application.port.in.command.SignUpCommand;
import com.practice.freeboard.application.port.in.command.WithdrawCommand;
import com.practice.freeboard.application.port.in.result.LoginResult;
import com.practice.freeboard.application.port.in.result.RefreshResult;
import com.practice.freeboard.application.port.in.usecase.LoginUseCase;
import com.practice.freeboard.application.port.in.usecase.LogoutUseCase;
import com.practice.freeboard.application.port.in.usecase.RefreshUseCase;
import com.practice.freeboard.application.port.in.usecase.SignUpUseCase;
import com.practice.freeboard.application.port.in.usecase.WithdrawUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 인증 관련 HTTP 입구.
 * 회원가입·로그인 URL을 /posts와 섞지 않으려고 /auth 로 분리한다.
 */
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    /** 회원가입 유스케이스. 컨트롤러는 포트만 의존 */
    private final SignUpUseCase signUpUseCase;

    /** 로그인 유스케이스. 성공 시 Access + Refresh 발급 */
    private final LoginUseCase loginUseCase;

    /** Access 재발급 (Refresh 유효할 때) */
    private final RefreshUseCase refreshUseCase;

    /** 로그아웃 — Refresh 1개 폐기 */
    private final LogoutUseCase logoutUseCase;

    /** 탈퇴 — Refresh 전부 폐기 + 회원 삭제 */
    private final WithdrawUseCase withdrawUseCase;

    /**
     * POST /auth/signup
     * Body: email, password → 201 + { "id": 회원번호 }
     */
    @PostMapping("/signup")
    public ResponseEntity<Map<String, Long>> signUp(@RequestBody @Valid SignUpRequestVo request) {

        // VO(웹) -> Command(애플리케이션)로 변환
        SignUpCommand command = new SignUpCommand(
                request.getEmail(),
                request.getPassword()
        );

        Long memberId = signUpUseCase.signUp(command);

        // 생성 성공은 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", memberId));
    }

    /**
     * POST /auth/login
     * Body: email, password
     * → 200 + { accessToken, refreshToken }
     * accessToken: API Header Bearer
     * refreshToken: 따로 보관했다가 Access 만료 때 /auth/refresh 에 사용
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid LoginRequestVo request) {

        LoginCommand command = new LoginCommand(
                request.getEmail(),
                request.getPassword()
        );

        LoginResult result = loginUseCase.login(command);

        return ResponseEntity.ok(Map.of(
                "accessToken", result.getAccessToken(),
                "refreshToken", result.getRefreshToken()
        ));
    }

    /**
     * POST /auth/refresh
     * Body: { "refreshToken": "..." }
     * → 새 accessToken + 새 refreshToken (회전)
     * Access 없이 호출해야 해서 Security에서 permitAll.
     */
    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(@RequestBody @Valid RefreshRequestVo request) {

        RefreshCommand command = new RefreshCommand(request.getRefreshToken());
        RefreshResult result = refreshUseCase.refresh(command);

        return ResponseEntity.ok(Map.of(
                "accessToken", result.getAccessToken(),
                "refreshToken", result.getRefreshToken()
        ));
    }

    /**
     * POST /auth/logout
     * Body: { "refreshToken": "..." }
     * → 200. 해당 Refresh만 폐기. 이후 /auth/refresh 는 401.
     * Access가 이미 만료됐을 수도 있어서 permitAll (Refresh로 대상 특정).
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody @Valid LogoutRequestVo request) {

        LogoutCommand command = new LogoutCommand(request.getRefreshToken());
        logoutUseCase.logout(command);

        return ResponseEntity.ok().build();
    }

    /**
     * DELETE /auth/withdraw
     * Body 없음. Header: Authorization Bearer Access 즉 JWT 컨텍스트에서 id값 꺼냄
     * → 200. 내 Refresh 전부 폐기 + member 삭제
     * permitAll 아님. Access 없으면 401 (anyRequest authenticated). 당연한 이유임 정상 회원만 탈퇴요청이 가능함.
     * memberId는 Body가 아니라 SecurityContext(필터가 JWT에서 넣음)에서 꺼낸다. 다른 id 값을 Body에 넣어서 탈퇴시키는걸 방지
     */
    @DeleteMapping("/withdraw")
    public ResponseEntity<Void> withdraw() {

        // 필터가 Bearer 검증 후 넣어 둔 인증 정보
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // principal = 로그인 때 JWT에 넣었던 memberId (Long)
        Long memberId = (Long) authentication.getPrincipal();

        withdrawUseCase.withdraw(new WithdrawCommand(memberId));

        return ResponseEntity.ok().build();
    }
}
