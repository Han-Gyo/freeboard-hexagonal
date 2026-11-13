package com.practice.freeboard.application.exception;

/**
 * 동시성·상태 경합 실패.
 * 예: 수락 WHERE 가 0건 → 이미 다른 수의사가 잡음.
 */
public class MatchConflictException extends RuntimeException {

    public MatchConflictException(String message) {
        super(message);
    }
}
