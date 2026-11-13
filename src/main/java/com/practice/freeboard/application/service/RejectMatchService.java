package com.practice.freeboard.application.service;

import com.practice.freeboard.application.exception.MatchConflictException;
import com.practice.freeboard.application.exception.MatchNotFoundException;
import com.practice.freeboard.application.port.in.command.RejectMatchCommand;
import com.practice.freeboard.application.port.in.usecase.RejectMatchUseCase;
import com.practice.freeboard.application.port.out.ReadMatchPort;
import com.practice.freeboard.application.port.out.RejectMatchPort;
import com.practice.freeboard.domain.model.MatchRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RejectMatchService implements RejectMatchUseCase {

    private final ReadMatchPort readMatchPort;
    private final RejectMatchPort rejectMatchPort;

    @Override
    @Transactional
    public void rejectMatch(RejectMatchCommand command) {

        MatchRequest match = readMatchPort.read(command.getMatchId())
                .orElseThrow(() -> new MatchNotFoundException(command.getMatchId()));

        // 도메인: REQUESTED 일 때만 거절
        match.reject();

        // DB: 대기 중일 때만 거절로 변경 (경합 시 0건)
        boolean ok = rejectMatchPort.rejectIfRequested(command.getMatchId());
        if (!ok) {
            throw new MatchConflictException("거절할 수 없는 상태입니다. (이미 처리됨)");
        }
    }
}
