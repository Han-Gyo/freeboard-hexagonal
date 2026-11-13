package com.practice.freeboard.application.service;

import com.practice.freeboard.application.exception.MatchConflictException;
import com.practice.freeboard.application.exception.MatchNotFoundException;
import com.practice.freeboard.application.port.in.command.CompleteMatchCommand;
import com.practice.freeboard.application.port.in.usecase.CompleteMatchUseCase;
import com.practice.freeboard.application.port.out.CompleteMatchPort;
import com.practice.freeboard.application.port.out.ReadMatchPort;
import com.practice.freeboard.domain.exception.InvalidMatchException;
import com.practice.freeboard.domain.model.MatchRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CompleteMatchService implements CompleteMatchUseCase {

    private final ReadMatchPort readMatchPort;
    private final CompleteMatchPort completeMatchPort;

    @Override
    @Transactional
    public void completeMatch(CompleteMatchCommand command) {

        MatchRequest match = readMatchPort.read(command.getMatchId())
                .orElseThrow(() -> new MatchNotFoundException(command.getMatchId()));

        Long memberId = command.getMemberId();
        boolean isOwner = match.getOwnerId().equals(memberId);
        boolean isAcceptedVet = match.getAcceptedVetId() != null
                && match.getAcceptedVetId().equals(memberId);

        // 기획: 보호자·수의사 양측에 상담 종료 버튼 (한쪽만 눌러도 완료)
        if (!isOwner && !isAcceptedVet) {
            throw new InvalidMatchException("이 매칭의 보호자 또는 수락한 수의사만 완료할 수 있습니다.");
        }

        match.complete();

        boolean ok = completeMatchPort.completeIfAccepted(command.getMatchId());
        if (!ok) {
            throw new MatchConflictException("완료할 수 없는 상태입니다.");
        }
    }
}
