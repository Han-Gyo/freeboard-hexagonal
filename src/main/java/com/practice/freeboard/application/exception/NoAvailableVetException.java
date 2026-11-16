package com.practice.freeboard.application.exception;

/**
 * 지금 ON 인 수의사가 한 명도 없을 때.
 * 매칭 요청을 입구에서 거절.
 */
public class NoAvailableVetException extends RuntimeException {

    public NoAvailableVetException() {
        super("지금은 상담 가능한 수의사가 없습니다.");
    }
}
