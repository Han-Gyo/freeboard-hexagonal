package com.practice.freeboard.application.service;

import com.practice.freeboard.application.exception.PostNotFoundException;
import com.practice.freeboard.application.port.in.usecase.DeletePostUseCase;
import com.practice.freeboard.application.port.out.DeletePostPort;
import com.practice.freeboard.application.port.out.ReadPostPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class DeletePostService implements DeletePostUseCase {

    // DB 조회 Port 객체 생성 (존재 여부 확인용)
    private final ReadPostPort readPostPort;
    // DB 삭제 Port 객체 생성 (삭제 출구 Port)
    private final DeletePostPort deletePostPort;

    @Override
    @Transactional
    public void delete(Long id) {

        // 클라이언트가 요청한 id값이 없으면 예외처리
        readPostPort.read(id).orElseThrow( () -> new PostNotFoundException(id));

        // 삭제 처리 삭제 port로 DB 삭제 ( 실제 동작 구현체는 adapter )
        deletePostPort.delete(id);

    }
}
