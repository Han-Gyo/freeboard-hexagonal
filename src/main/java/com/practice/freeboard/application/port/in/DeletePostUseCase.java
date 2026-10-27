package com.practice.freeboard.application.port.in;

public interface DeletePostUseCase {

    // id로 삭제. 없으면 PostNotFoundException으로 예외처리
    void delete (Long id);

}
