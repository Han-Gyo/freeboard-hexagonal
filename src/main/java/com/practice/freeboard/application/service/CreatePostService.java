package com.practice.freeboard.application.service;

import com.practice.freeboard.application.port.in.command.CreatePostCommand;
import com.practice.freeboard.application.port.in.usecase.CreatePostUseCase;
import com.practice.freeboard.application.port.out.CreatePostPort;
import com.practice.freeboard.domain.model.Post;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service    // 이 클래스는 비즈니스 로직을 처리하는 클래스다.
@AllArgsConstructor
public class CreatePostService implements CreatePostUseCase {

    private final CreatePostPort createPostPort;

    @Override
    public Long createPost(CreatePostCommand createPostCommand) {

        // dto를 도메인에 넣고 검증하기
        Post post = new Post(null,
                createPostCommand.getTitle(),
                createPostCommand.getContent(),
                createPostCommand.getAuthorName(),
                LocalDateTime.now());

        // 도메인 db로 가는 출구인 port로 보내고 저장 완료 되면 결과값 받아오기
        Post created = createPostPort.createPost(post);

        // id 값을 컨트롤러에게 넘기기   -> 다음 단계는 도메인을 엔터티로 매핑해야 하기 때문에 엔터티 만들러가자
        return created.getId();
    }
}
