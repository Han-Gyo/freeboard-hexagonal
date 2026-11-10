package com.practice.freeboard.adapter.in.web;

import com.practice.freeboard.adapter.in.web.vo.LoginRequestVo;
import com.practice.freeboard.adapter.in.web.vo.SignUpRequestVo;
import com.practice.freeboard.application.port.in.command.LoginCommand;
import com.practice.freeboard.application.port.in.command.SignUpCommand;
import com.practice.freeboard.application.port.in.result.LoginResult;
import com.practice.freeboard.application.port.in.usecase.LoginUseCase;
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

    /** 로그인 유스케이스. 성공 시 액세스 토큰 발급 */
    private final LoginUseCase loginUseCase;

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
     * Body: email, password → 200 + { "accessToken": "JWT..." }
     * 클라이언트가 이 토큰을 저장했다가 다음 요청 Header에 넣는다.
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid LoginRequestVo request) {

        LoginCommand command = new LoginCommand(
                request.getEmail(),
                request.getPassword()
        );

        LoginResult result = loginUseCase.login(command);

        return ResponseEntity.ok(Map.of("accessToken", result.getAccessToken()));
    }
}
