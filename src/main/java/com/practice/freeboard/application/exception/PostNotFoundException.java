package com.practice.freeboard.application.exception;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(Long id) {
        super("게시글 없음: " + id);
    }
}
