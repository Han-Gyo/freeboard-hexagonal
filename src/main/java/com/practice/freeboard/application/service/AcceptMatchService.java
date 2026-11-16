package com.practice.freeboard.application.service;

import com.practice.freeboard.application.exception.MatchConflictException;
import com.practice.freeboard.application.exception.MatchNotFoundException;
import com.practice.freeboard.application.port.in.command.AcceptMatchCommand;
import com.practice.freeboard.application.port.in.usecase.AcceptMatchUseCase;
import com.practice.freeboard.application.port.out.AcceptMatchPort;
import com.practice.freeboard.application.port.out.LoginPort;
import com.practice.freeboard.application.port.out.ReadMatchPort;
import com.practice.freeboard.domain.exception.InvalidMemberException;
import com.practice.freeboard.domain.model.MatchRequest;
import com.practice.freeboard.domain.model.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 수의사 선착순 수락.
 *
 * 카카오T 느낌:
 * - 보호자 요청은 일단 REQUESTED 로 쌓임 (ON 수의사 없어도 요청 자체는 받음)
 * - 수락은 "지금 ON 인 수의사"만 가능
 * - 배차 실패(타임아웃 등)는 나중에 별도 상태/스케줄로
 */
@Service
@AllArgsConstructor
public class AcceptMatchService implements AcceptMatchUseCase {

    private final ReadMatchPort readMatchPort;
    private final AcceptMatchPort acceptMatchPort;
    // 수락하는 사람이 지금 ON 인지 보려고 회원 조회
    private final LoginPort loginPort;

    @Override
    @Transactional
    public void acceptMatch(AcceptMatchCommand command) {

        // 1) 매칭 건 있는지
        MatchRequest match = readMatchPort.read(command.getMatchId())
                .orElseThrow(() -> new MatchNotFoundException(command.getMatchId()));

        // 2) 수락하려는 수의사가 상담 가능 ON 인지
        //    OFF 면 배차 잡으면 안 됨 (라디오 끈 상태)
        Member vet = loginPort.findById(command.getVetId())
                .orElseThrow(() -> new InvalidMemberException("수의사 정보를 찾을 수 없습니다."));
        if (!vet.isMatchingAvailable()) {
            throw new InvalidMemberException("상담 가능 상태가 아니라서 수락할 수 없습니다.");
        }

        // 3) 도메인 규칙 (이미 수락/거절/취소면 여기서 막힘)
        match.accept(command.getVetId());

        // 4) DB 선착순: WHERE status=REQUESTED 가 1건일 때만 성공
        boolean won = acceptMatchPort.acceptIfRequested(command.getMatchId(), command.getVetId());
        if (!won) {
            throw new MatchConflictException("이미 다른 수의사가 수락한 요청입니다.");
        }
    }
}
