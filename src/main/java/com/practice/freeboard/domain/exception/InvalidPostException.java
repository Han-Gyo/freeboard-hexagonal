package com.practice.freeboard.domain.exception;

// 게시글 업무 규칙 위반 시 사용되는 예외 ( ex. 금지어, 중복 등등 )
public class InvalidPostException extends RuntimeException{

    // 프론트 / Advice에 내려줄 메시지    Invalid = 유효하지 않은
    public InvalidPostException(String message) {

        super(message);

    }
}
