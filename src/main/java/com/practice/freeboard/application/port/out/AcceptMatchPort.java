package com.practice.freeboard.application.port.out;

/**
 * 선착순 수락 출구.
 * Adapter에서 UPDATE ... WHERE id=? AND status='REQUESTED' 로 구현.
 * true = 내가 잡음, false = 이미 다른 사람이 잡음(또는 상태 변경됨).
 */
public interface AcceptMatchPort {

    boolean acceptIfRequested(Long matchId, Long vetId);
}
