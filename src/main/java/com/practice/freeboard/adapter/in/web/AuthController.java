package com.practice.freeboard.adapter.in.web;

import com.practice.freeboard.adapter.in.web.vo.LoginRequestVo;
import com.practice.freeboard.adapter.in.web.vo.RefreshRequestVo;
import com.practice.freeboard.adapter.in.web.vo.SignUpRequestVo;
import com.practice.freeboard.application.port.in.command.LoginCommand;
import com.practice.freeboard.application.port.in.command.RefreshCommand;
import com.practice.freeboard.application.port.in.command.SignUpCommand;
import com.practice.freeboard.application.port.in.result.LoginResult;
import com.practice.freeboard.application.port.in.result.RefreshResult;
import com.practice.freeboard.application.port.in.usecase.LoginUseCase;
import com.practice.freeboard.application.port.in.usecase.RefreshUseCase;
import com.practice.freeboard.application.port.in.usecase.SignUpUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
