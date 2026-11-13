package com.practice.freeboard.adapter.out.mysql;

import com.practice.freeboard.adapter.out.mysql.repository.MatchRequestJpaRepository;
import com.practice.freeboard.application.port.out.RejectMatchPort;
import com.practice.freeboard.domain.model.MatchStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RejectMatchAdapter implements RejectMatchPort {

    private final MatchRequestJpaRepository matchRequestJpaRepository;

    @Override
    public boolean rejectIfRequested(Long matchId) {
        int updated = matchRequestJpaRepository.rejectIfRequested(
                matchId,
                MatchStatus.REQUESTED,
                MatchStatus.REJECTED
        );
        return updated == 1;
    }
}
