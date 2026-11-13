package com.practice.freeboard.application.port.out;

import com.practice.freeboard.domain.model.MatchRequest;

/** 매칭 요청 저장 출구 (구현은 Adapter) */
public interface CreateMatchPort {

    MatchRequest createMatch(MatchRequest matchRequest);
}
