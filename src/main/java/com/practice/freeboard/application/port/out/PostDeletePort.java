package com.practice.freeboard.application.port.out;

public interface PostDeletePort {

    // id로 DB에서 데이터 삭제
    void delete(Long id);

}
