package com.practice.freeboard.adapter.out.mysql;

import com.practice.freeboard.adapter.out.mysql.repository.MatchRequestJpaRepository;
import com.practice.freeboard.application.port.out.AcceptMatchPort;
import com.practice.freeboard.domain.model.MatchStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AcceptMatchAdapter implements AcceptMatchPort {

    private final MatchRequestJpaRepository matchRequestJpaRepository;

    @Override
    public boolean acceptIfRequested(Long matchId, Long vetId) {
        // UPDATE ... WHERE status=REQUESTED → 영향 행 1이면 성공
        int updated = matchRequestJpaRepository.acceptIfRequested(
                matchId,
                vetId,
                MatchStatus.REQUESTED,
                MatchStatus.ACCEPTED
        );
        return updated == 1;
    }
}
