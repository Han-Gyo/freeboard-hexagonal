package com.practice.freeboard.application.service;

import com.practice.freeboard.application.exception.PostNotFoundException;
import com.practice.freeboard.application.port.in.usecase.ReadPostUseCase;
import com.practice.freeboard.application.port.out.ReadPostPort;
import com.practice.freeboard.domain.model.Post;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ReadPostService implements ReadPostUseCase {

    // Port 인터페이스
    private final ReadPostPort readPostPort;

    @Override
    @Transactional(readOnly = true)
    public Post read(Long id) {

        // 가져온 id값 db에 조회하라고 port에 보내고 조회 결과 가져오
        return readPostPort.read(id).orElseThrow(() -> new PostNotFoundException(id));
    }
}
