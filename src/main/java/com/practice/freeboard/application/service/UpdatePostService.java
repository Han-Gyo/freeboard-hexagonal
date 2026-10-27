package com.practice.freeboard.application.service;

import com.practice.freeboard.application.exception.PostNotFoundException;
import com.practice.freeboard.application.port.in.UpdatePostUseCase;
import com.practice.freeboard.application.port.in.dto.UpdatePostCommand;
import com.practice.freeboard.application.port.out.PostLoadPort;
import com.practice.freeboard.application.port.out.PostSavePort;
import com.practice.freeboard.domain.model.Post;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdatePostService implements UpdatePostUseCase {

    private final PostLoadPort postLoadPort;
    private final PostSavePort postSavePort;

    public UpdatePostService(PostLoadPort postLoadPort, PostSavePort portSavePort) {
        this.postLoadPort = postLoadPort;
        this.postSavePort = portSavePort;
    }

    @Override
    @Transactional
    public void update(UpdatePostCommand command) {

        // 기존 글 조회
        Post existing = postLoadPort.findById(command.getId())
                .orElseThrow(() -> new PostNotFoundException(command.getId()));

        // 수정된 Domain ( setter 사용 안 하니 새로 생성, id, type, createdAt은 유지 )
        Post updated = new Post (
                existing.getId(),
                command.getTitle(),
                command.getContent(),
                command.getAuthorName(),
                existing.getPostType(),
                existing.getCreatedAt()
        );

        // 저장
        postSavePort.save(updated);

    }
}
