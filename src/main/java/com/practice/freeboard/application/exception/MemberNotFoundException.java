package com.practice.freeboard.application.exception;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException(Long id) {
        super("회원 없음: " + id);
    }
}
