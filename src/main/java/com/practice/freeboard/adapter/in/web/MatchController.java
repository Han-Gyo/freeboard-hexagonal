package com.practice.freeboard.adapter.in.web;

import com.practice.freeboard.adapter.in.web.vo.CreateMatchRequestVo;
import com.practice.freeboard.application.port.in.command.AcceptMatchCommand;
import com.practice.freeboard.application.port.in.command.CancelMatchCommand;
import com.practice.freeboard.application.port.in.command.CompleteMatchCommand;
import com.practice.freeboard.application.port.in.command.CreateMatchCommand;
import com.practice.freeboard.application.port.in.command.RejectMatchCommand;
import com.practice.freeboard.application.port.in.usecase.AcceptMatchUseCase;
import com.practice.freeboard.application.port.in.usecase.CancelMatchUseCase;
import com.practice.freeboard.application.port.in.usecase.CompleteMatchUseCase;
import com.practice.freeboard.application.port.in.usecase.CreateMatchUseCase;
import com.practice.freeboard.application.port.in.usecase.RejectMatchUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 매칭 HTTP 입구.
 * 로그인 회원 id 는 JWT → SecurityContext principal.
 */
@RestController
@RequestMapping("/matches")
@AllArgsConstructor
public class MatchController {

    private final CreateMatchUseCase createMatchUseCase;
    private final AcceptMatchUseCase acceptMatchUseCase;
    private final RejectMatchUseCase rejectMatchUseCase;
    private final CancelMatchUseCase cancelMatchUseCase;
    private final CompleteMatchUseCase completeMatchUseCase;

    /** 현재 요청의 로그인 회원 id (필터가 JWT에서 넣음) */
    private Long currentMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Long) authentication.getPrincipal();
    }

    /**
     * POST /matches
     * 보호자 매칭 요청 → 201 + { "id": 매칭번호 }
     */
    @PostMapping
    public ResponseEntity<Map<String, Long>> createMatch(
            @RequestBody @Valid CreateMatchRequestVo request
    ) {
        Long ownerId = currentMemberId();

        CreateMatchCommand command = new CreateMatchCommand(
                ownerId,
                request.getSymptom(),
                request.getConsultType(),
                request.getPetName(),
                request.getPetBreed(),
                request.getPetAge(),
                request.getPetWeight(),
                request.getPetGender()
        );

        Long matchId = createMatchUseCase.createMatch(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", matchId));
    }

    /**
     * POST /matches/{id}/accept
     * 수의사 선착순 수락
     */
    @PostMapping("/{id}/accept")
    public ResponseEntity<Map<String, String>> accept(@PathVariable Long id) {
        Long vetId = currentMemberId();
        acceptMatchUseCase.acceptMatch(new AcceptMatchCommand(id, vetId));
        return ResponseEntity.ok(Map.of("message", "수락 완료"));
    }

    /**
     * POST /matches/{id}/reject
     * 수의사 거절
     */
    @PostMapping("/{id}/reject")
    public ResponseEntity<Map<String, String>> reject(@PathVariable Long id) {
        Long vetId = currentMemberId();
        rejectMatchUseCase.rejectMatch(new RejectMatchCommand(id, vetId));
        return ResponseEntity.ok(Map.of("message", "거절 완료"));
    }

    /**
     * POST /matches/{id}/cancel
     * 보호자 요청 취소
     */
    @PostMapping("/{id}/cancel")
    public ResponseEntity<Map<String, String>> cancel(@PathVariable Long id) {
        Long ownerId = currentMemberId();
        cancelMatchUseCase.cancelMatch(new CancelMatchCommand(id, ownerId));
        return ResponseEntity.ok(Map.of("message", "취소 완료"));
    }

    /**
     * POST /matches/{id}/complete
     * 상담 완료 — 보호자·수의사 둘 다 가능 (한쪽만 눌러도 종료)
     */
    @PostMapping("/{id}/complete")
    public ResponseEntity<Map<String, String>> complete(@PathVariable Long id) {
        Long memberId = currentMemberId();
        completeMatchUseCase.completeMatch(new CompleteMatchCommand(id, memberId));
        return ResponseEntity.ok(Map.of("message", "완료 처리"));
    }
}
