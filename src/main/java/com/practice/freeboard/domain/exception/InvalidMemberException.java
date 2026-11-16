package com.practice.freeboard.domain.exception;

// 회원 업무 규칙 위반 (예: 수의사만 ON/OFF 가능)
public class InvalidMemberException extends RuntimeException {

    public InvalidMemberException(String message) {
        super(message);
    }
}
