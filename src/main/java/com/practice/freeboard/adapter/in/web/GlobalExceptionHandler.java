package com.practice.freeboard.adapter.in.web;

import com.practice.freeboard.application.exception.DuplicateEmailException;
import com.practice.freeboard.application.exception.LoginFailedException;
import com.practice.freeboard.application.exception.PostNotFoundException;
import com.practice.freeboard.domain.exception.InvalidPostException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    // 이메일 중복 가입 → 409 Conflict (요청은 이해했지만 상태 충돌)
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<Map<String, String>> handlerDuplicateEmail(DuplicateEmailException e) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", e.getMessage()));
    }

    // 로그인 실패 → 401 Unauthorized (인증 실패)
    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<Map<String, String>> handlerLoginFailed(LoginFailedException e) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", e.getMessage()));
    }

    // @Valid 형식 검증 실패 → 400 JSON 통일시키기 통일 안 시키면 JSON 모양이 제각각임
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handlerValidation(MethodArgumentNotValidException e){

        // 400고정은 HttpStatus.BAD_REQUEST임
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "입력값이 올바르지 않습니다."));
    }

    // Domain 규칙 위반 ( 금지어 등등 ) -> 400
    @ExceptionHandler(InvalidPostException.class)
    public ResponseEntity<Map<String, String>> handlerInvalidPost(InvalidPostException e){

        // 규칙 위반은 잘못된 요청이라 HttpStatus.BAD_REQUEST임
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));

    }

}
