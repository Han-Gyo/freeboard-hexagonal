package com.practice.freeboard.application.port.out;

/**
 * 취소 출구.
 * UPDATE ... WHERE id=? AND status='REQUESTED'
 */
public interface CancelMatchPort {

    boolean cancelIfRequested(Long matchId);
}
