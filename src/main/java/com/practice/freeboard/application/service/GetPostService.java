package com.practice.freeboard.application.service;

import com.practice.freeboard.application.exception.PostNotFoundException;
import com.practice.freeboard.application.port.in.GetPostUseCase;
import com.practice.freeboard.application.port.out.PostLoadPort;
import com.practice.freeboard.domain.model.Post;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetPostService implements GetPostUseCase {

    // DB 조회는 Port 뒤에 숨김 ( 실제로는 PostLoadAdapter 에서 구현됨 )
    private final PostLoadPort postLoadPort;

    public GetPostService(PostLoadPort postLoadPort) {
        this.postLoadPort = postLoadPort;
    }

    @Override
    @Transactional(readOnly = true)  // 조회만 하나 읽기 전용
    public Post getById(Long id) {
        // 있으면 Post, 없으면 예외
        return postLoadPort.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }
}
