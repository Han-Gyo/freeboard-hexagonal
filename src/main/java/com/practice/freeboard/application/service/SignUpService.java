package com.practice.freeboard.application.service;

import com.practice.freeboard.application.exception.DuplicateEmailException;
import com.practice.freeboard.application.port.in.command.SignUpCommand;
import com.practice.freeboard.application.port.in.usecase.SignUpUseCase;
import com.practice.freeboard.application.port.out.SignUpPort;
import com.practice.freeboard.domain.model.Member;
import com.practice.freeboard.domain.model.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 회원가입 유스케이스 구현.
 * 왜 여기냐면: "중복 검사 → 비번 해시 → 기본 역할 부여 → 저장" 이 흐름이
 * 웹도 DB도 아닌 애플리케이션 규칙이기 때문.
 */
@Service
@AllArgsConstructor
public class SignUpService implements SignUpUseCase {

    /** DB 저장/조회 출구 */
    private final SignUpPort signUpPort;

    /**
     * SecurityConfig에 @Bean으로 등록해 둔 BCrypt 인코더.
     * 스프링이 생성자로 주입해 준다.
     */
    private final PasswordEncoder passwordEncoder;

    @Override
    public Long signUp(SignUpCommand command) {

        // 1) 이메일 중복이면 가입 불가
        if (signUpPort.existsByEmail(command.getEmail())) {
            throw new DuplicateEmailException(command.getEmail());
        }

        // 2) 평문 비번 → BCrypt 해시. DB에는 이 해시만 저장한다.
        String encodedPassword = passwordEncoder.encode(command.getPassword());

        // 3) 도메인 회원 만들기. 자가가입은 기본 USER (ADMIN 자기등록 막기)
        Member member = new Member(
                null,
                command.getEmail(),
                encodedPassword,
                Role.USER,
                LocalDateTime.now()
        );

        // 4) 출구로 저장하고, DB가 채운 id를 컨트롤러에 반환
        Member saved = signUpPort.save(member);
        return saved.getId();
    }
}
