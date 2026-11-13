package com.practice.freeboard.application.port.out;

/**
 * 거절 출구.
 * UPDATE ... WHERE id=? AND status='REQUESTED'
 */
public interface RejectMatchPort {

    boolean rejectIfRequested(Long matchId);
}
