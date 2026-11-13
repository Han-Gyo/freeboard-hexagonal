package com.practice.freeboard.adapter.out.mysql;

import com.practice.freeboard.adapter.out.mysql.repository.MatchRequestJpaRepository;
import com.practice.freeboard.application.port.out.CancelMatchPort;
import com.practice.freeboard.domain.model.MatchStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CancelMatchAdapter implements CancelMatchPort {

    private final MatchRequestJpaRepository matchRequestJpaRepository;

    @Override
    public boolean cancelIfRequested(Long matchId) {
        int updated = matchRequestJpaRepository.cancelIfRequested(
                matchId,
                MatchStatus.REQUESTED,
                MatchStatus.CANCELLED
        );
        return updated == 1;
    }
}
