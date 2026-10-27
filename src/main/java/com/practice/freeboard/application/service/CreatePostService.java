package com.practice.freeboard.application.service;


import com.practice.freeboard.application.port.in.CreatePostUseCase;
import com.practice.freeboard.application.port.in.dto.CreatePostCommand;
import com.practice.freeboard.application.port.out.CreatePostPort;
import com.practice.freeboard.domain.model.Post;
import com.practice.freeboard.domain.model.PostType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CreatePostService implements CreatePostUseCase {

    private final CreatePostPort createPostPort;

    public CreatePostService(CreatePostPort createPostPort) {
        this.createPostPort = createPostPort;
    }

    @Override
    @Transactional
    public Long create(CreatePostCommand createPostCommand) {

        // domain 객체 생성 후 필드 초기화
        Post post = new Post (null,
                createPostCommand.getTitle(),
                createPostCommand.getContent(),
                createPostCommand.getAuthorName(),
                PostType.FREE,
                LocalDateTime.now());

        // createPostPort 안에 save 함수에 파라미터 값에 필드가 초기화가 된 도메인 넣기
        Post saved = createPostPort.save(post);

        // DB에 저장된 이후 자동생성 되는 id값 반환해서 컨트롤러에 전달
        return saved.getId();
    }
}
