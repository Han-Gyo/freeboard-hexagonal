package com.practice.freeboard.application.service;

import com.practice.freeboard.application.exception.MatchConflictException;
import com.practice.freeboard.application.exception.MatchNotFoundException;
import com.practice.freeboard.application.port.in.command.AcceptMatchCommand;
import com.practice.freeboard.application.port.in.usecase.AcceptMatchUseCase;
import com.practice.freeboard.application.port.out.AcceptMatchPort;
import com.practice.freeboard.application.port.out.ReadMatchPort;
import com.practice.freeboard.domain.model.MatchRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AcceptMatchService implements AcceptMatchUseCase {

    private final ReadMatchPort readMatchPort;
    private final AcceptMatchPort acceptMatchPort;

    @Override
    @Transactional
    public void acceptMatch(AcceptMatchCommand command) {

        // 1) 있는지 조회
        MatchRequest match = readMatchPort.read(command.getMatchId())
                .orElseThrow(() -> new MatchNotFoundException(command.getMatchId()));

        // 2) 도메인 규칙 (이미 수락/거절/취소면 여기서 막힘)
        match.accept(command.getVetId());

        // 3) DB 선착순: WHERE status=REQUESTED 가 1건일 때만 성공
        boolean won = acceptMatchPort.acceptIfRequested(command.getMatchId(), command.getVetId());
        if (!won) {
            throw new MatchConflictException("이미 다른 수의사가 수락한 요청입니다.");
        }
    }
}
