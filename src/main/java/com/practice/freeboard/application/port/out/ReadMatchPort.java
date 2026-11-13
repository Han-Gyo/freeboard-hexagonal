package com.practice.freeboard.application.port.out;

import com.practice.freeboard.domain.model.MatchRequest;

import java.util.Optional;

/** 매칭 요청 단건 조회 출구 */
public interface ReadMatchPort {

    Optional<MatchRequest> read(Long matchId);
}
