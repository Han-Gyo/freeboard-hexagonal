package com.practice.freeboard.application.service;

import com.practice.freeboard.application.port.in.CreatePostUseCase;
import com.practice.freeboard.application.port.in.dto.CreatePostCommand;
import com.practice.freeboard.application.port.out.PostSavePort;
import com.practice.freeboard.domain.model.Post;
import com.practice.freeboard.domain.model.PostType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CreatePostService implements CreatePostUseCase {

    // 바깥(DB)으로 나가는 포트 - 실제로는 PostSaveAdapter가 붙음
    private final PostSavePort postSavePort;

    public CreatePostService(PostSavePort postSavePort) {
        this.postSavePort = postSavePort;
    }


    @Override
    @Transactional  // 이 메서드 안에서 DB 작업 하나의 단위로 묶음
    public Long create(CreatePostCommand command) {
        // 1) 커맨드 -> 도메인 Post (아직 id 없음)
        Post post = new Post(
                null,
                command.getTitle(),
                command.getContent(),
                command.getAuthorName(),
                PostType.FREE,              // 지금은 자유게시판 고정
                LocalDateTime.now()
        );

        // 2) 저장 (Adapter -> DB ) 후 id 채운 Post가 돌아옴
        Post saved = postSavePort.save(post);

        // 3) Controller가 201 응답에 쓸 id
        return saved.getId();
    }
}
