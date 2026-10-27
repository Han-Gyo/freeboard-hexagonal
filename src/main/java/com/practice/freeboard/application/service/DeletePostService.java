package com.practice.freeboard.application.service;

import com.practice.freeboard.application.exception.PostNotFoundException;
import com.practice.freeboard.application.port.in.DeletePostUseCase;
import com.practice.freeboard.application.port.out.PostDeletePort;
import com.practice.freeboard.application.port.out.PostLoadPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeletePostService implements DeletePostUseCase {

    private final PostLoadPort postLoadPort;
    private final PostDeletePort postDeletePort;

    public DeletePostService(PostLoadPort postLoadPort, PostDeletePort postDeletePort) {
        this.postLoadPort = postLoadPort;
        this.postDeletePort = postDeletePort;
    }

    @Override
    @Transactional
    public void delete(Long id) {

        // 해당 id 가져오고 ( 없을 경우 예외처리 )
        postLoadPort.findById(id).orElseThrow(() -> new PostNotFoundException(id));

        // 해당 id 삭제
        postDeletePort.delete(id);

    }
}
