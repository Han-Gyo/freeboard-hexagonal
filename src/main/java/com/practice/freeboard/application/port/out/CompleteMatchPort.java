package com.practice.freeboard.application.port.out;

/**
 * 완료 출구.
 * UPDATE ... WHERE id=? AND status='ACCEPTED'
 */
public interface CompleteMatchPort {

    boolean completeIfAccepted(Long matchId);
}
