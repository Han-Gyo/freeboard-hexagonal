package com.practice.freeboard.application.exception;

/**
 * Refresh가 없거나, 만료됐거나, 폐기됐을 때.
 * 클라는 다시 로그인해야 함.
 */
public class InvalidRefreshTokenException extends RuntimeException {

    public InvalidRefreshTokenException() {
        super("Refresh 토큰이 유효하지 않습니다. 다시 로그인해주세요.");
    }
}
