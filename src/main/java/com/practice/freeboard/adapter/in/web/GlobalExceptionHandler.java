package com.practice.freeboard.adapter.in.web;

import com.practice.freeboard.application.exception.PostNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

// RestController에서 일어나는 모든 예외를 여기서 처리함.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 어느 클래스의 Exception인지 명시하기 ex) (PostNotFoundException.class)
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlerPostNotFound(PostNotFoundException e){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
    }

}
