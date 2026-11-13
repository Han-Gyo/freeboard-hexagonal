package com.practice.freeboard.application.service;

import com.practice.freeboard.application.exception.MatchConflictException;
import com.practice.freeboard.application.exception.MatchNotFoundException;
import com.practice.freeboard.application.port.in.command.CancelMatchCommand;
import com.practice.freeboard.application.port.in.usecase.CancelMatchUseCase;
import com.practice.freeboard.application.port.out.CancelMatchPort;
import com.practice.freeboard.application.port.out.ReadMatchPort;
import com.practice.freeboard.domain.exception.InvalidMatchException;
import com.practice.freeboard.domain.model.MatchRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CancelMatchService implements CancelMatchUseCase {

    private final ReadMatchPort readMatchPort;
    private final CancelMatchPort cancelMatchPort;

    @Override
    @Transactional
    public void cancelMatch(CancelMatchCommand command) {

        MatchRequest match = readMatchPort.read(command.getMatchId())
                .orElseThrow(() -> new MatchNotFoundException(command.getMatchId()));

        // 본인 요청만 취소
        if (!match.getOwnerId().equals(command.getOwnerId())) {
            throw new InvalidMatchException("본인의 매칭 요청만 취소할 수 있습니다.");
        }

        match.cancel();

        boolean ok = cancelMatchPort.cancelIfRequested(command.getMatchId());
        if (!ok) {
            throw new MatchConflictException("취소할 수 없는 상태입니다. (이미 처리됨)");
        }
    }
}
