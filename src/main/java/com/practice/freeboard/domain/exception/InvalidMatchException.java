package com.practice.freeboard.domain.exception;

// 매칭 업무 규칙 위반 시 사용 (예: 이미 수락된 요청을 또 수락)
public class InvalidMatchException extends RuntimeException {

    public InvalidMatchException(String message) {
        super(message);
    }
}
