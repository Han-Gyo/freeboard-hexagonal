package com.practice.freeboard.application.port.out;

/**
 * 매칭 가능 ON/OFF DB 반영 출구.
 * Adapter에서 UPDATE matching_available 로 구현.
 * true = 1행 갱신 성공, false = 해당 회원 없음 등
 */
public interface UpdateMatchingAvailablePort {

    boolean updateMatchingAvailable(Long memberId, boolean available);
}
