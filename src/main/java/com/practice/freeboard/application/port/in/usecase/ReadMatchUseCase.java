package com.practice.freeboard.application.port.in.usecase;

import com.practice.freeboard.domain.model.MatchRequest;

/**
 * 매칭 요청 단건 조회.
 * 보호자: 내 요청 상태 확인 (찾는 중 / 수락됨 등)
 * 수의사: 수락·거절 전 상세 보기
 */
public interface ReadMatchUseCase {

    MatchRequest read(Long matchId);
}
