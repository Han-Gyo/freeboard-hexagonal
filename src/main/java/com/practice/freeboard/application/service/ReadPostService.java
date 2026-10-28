package com.practice.freeboard.application.service;

import com.practice.freeboard.application.exception.PostNotFoundException;
import com.practice.freeboard.application.port.in.ReadPostUseCase;
import com.practice.freeboard.application.port.out.ReadOnePostPort;
import com.practice.freeboard.domain.model.Post;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ReadPostService implements ReadPostUseCase {

    // DB 조회할 Port 객체 생성
    private final ReadOnePostPort readOnePostPort;

    @Override
    @Transactional(readOnly = true)
    public Post getById(Long id) {

        // DB에 요청한 id값 조회하기 없을경우 예외처리 ( 예외 처리 구현해놓은 곳은 exception 폴더안에 Exception 클래스 있음 )
        return readOnePostPort.getById(id).
                orElseThrow(() -> new PostNotFoundException(id))  ;
    }
}
