package com.practice.freeboard.adapter.in.web;

import com.practice.freeboard.adapter.in.web.vo.CreatePostRequestVo;
import com.practice.freeboard.application.port.in.command.CreatePostCommand;
import com.practice.freeboard.application.port.in.usecase.CreatePostUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {

    private final CreatePostUseCase createPostUseCase;

    @PostMapping
    public ResponseEntity<Map<String, Long>> createPost (@RequestBody CreatePostRequestVo createPostRequestVo){

        // vo -> dto
        CreatePostCommand createPostCommand = new CreatePostCommand(
                createPostRequestVo.getTitle(),
                createPostRequestVo.getContent(),
                createPostRequestVo.getAuthorName()
        );

        // useCase 접근하기
        Long id = createPostUseCase.createPost(createPostCommand);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", id));

    }

}
