package com.practice.freeboard.adapter.in.web;

import com.practice.freeboard.adapter.in.web.vo.ChangeMatchingAvailableRequestVo;
import com.practice.freeboard.application.port.in.command.ChangeMatchingAvailableCommand;
import com.practice.freeboard.application.port.in.usecase.ChangeMatchingAvailableUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 회원 관련 HTTP 입구.
 * 지금은 수의사 상담 가능 ON/OFF 만.
 */
@RestController
@RequestMapping("/members")
@AllArgsConstructor
public class MemberController {

    private final ChangeMatchingAvailableUseCase changeMatchingAvailableUseCase;

    /** JWT 필터가 넣어 둔 로그인 회원 id */
    private Long currentMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Long) authentication.getPrincipal();
    }

    /**
     * PATCH /members/me/matching-available
     * Body: { "available": true|false }
     * Header: Bearer Access (VET만 Security에서 통과)
     */
    @PatchMapping("/me/matching-available")
    public ResponseEntity<Map<String, String>> changeMatchingAvailable(
            @RequestBody @Valid ChangeMatchingAvailableRequestVo request
    ) {
        Long memberId = currentMemberId();

        // Vo → Command (id는 JWT, available은 Body)
        ChangeMatchingAvailableCommand command = new ChangeMatchingAvailableCommand(
                memberId,
                request.getAvailable()
        );

        changeMatchingAvailableUseCase.changeMatchingAvailable(command);

        return ResponseEntity.ok(Map.of("message", "상담 가능 여부 변경 완료"));
    }
}
