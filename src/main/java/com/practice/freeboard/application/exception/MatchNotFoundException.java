package com.practice.freeboard.application.exception;

public class MatchNotFoundException extends RuntimeException {

    public MatchNotFoundException(Long id) {
        super("매칭 요청 없음: " + id);
    }
}
