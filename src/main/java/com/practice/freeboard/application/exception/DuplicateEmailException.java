package com.practice.freeboard.application.exception;

/**
 * 이미 있는 이메일로 가입 시도할 때.
 * 컨트롤러까지 예외를 올려 GlobalExceptionHandler가 409 JSON으로 바꾼다.
 */
public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String email) {
        super("이미 가입된 이메일입니다: " + email);
    }
}
