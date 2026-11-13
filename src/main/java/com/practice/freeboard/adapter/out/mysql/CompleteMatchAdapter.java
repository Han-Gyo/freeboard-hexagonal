package com.practice.freeboard.adapter.out.mysql;

import com.practice.freeboard.adapter.out.mysql.repository.MatchRequestJpaRepository;
import com.practice.freeboard.application.port.out.CompleteMatchPort;
import com.practice.freeboard.domain.model.MatchStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CompleteMatchAdapter implements CompleteMatchPort {

    private final MatchRequestJpaRepository matchRequestJpaRepository;

    @Override
    public boolean completeIfAccepted(Long matchId) {
        int updated = matchRequestJpaRepository.completeIfAccepted(
                matchId,
                MatchStatus.ACCEPTED,
                MatchStatus.COMPLETED
        );
        return updated == 1;
    }
}
