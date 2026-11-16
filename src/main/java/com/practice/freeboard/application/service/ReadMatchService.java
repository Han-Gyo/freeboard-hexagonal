package com.practice.freeboard.application.service;

import com.practice.freeboard.application.exception.MatchNotFoundException;
import com.practice.freeboard.application.port.in.usecase.ReadMatchUseCase;
import com.practice.freeboard.application.port.out.ReadMatchPort;
import com.practice.freeboard.domain.model.MatchRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 매칭 단건 조회.
 * 흐름: matchId → ReadMatchPort → 없으면 404 / 있으면 도메인 반환
 *
 * 누가 볼 수 있는지는 다음 단계(Controller)에서 JWT로 더 조일 수 있음.
 * 지금은 "있는 건 읽기" 까지.
 */
@Service
@AllArgsConstructor
public class ReadMatchService implements ReadMatchUseCase {

    private final ReadMatchPort readMatchPort;

    @Override
    @Transactional(readOnly = true)
    public MatchRequest read(Long matchId) {
        return readMatchPort.read(matchId)
                .orElseThrow(() -> new MatchNotFoundException(matchId));
    }
}
