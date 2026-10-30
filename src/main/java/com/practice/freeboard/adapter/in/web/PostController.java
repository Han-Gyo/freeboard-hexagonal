package com.practice.freeboard.adapter.in.web;

import com.practice.freeboard.adapter.in.web.vo.CreatePostRequestVo;
import com.practice.freeboard.adapter.in.web.vo.ReadPostResponseVo;
import com.practice.freeboard.application.port.in.command.CreatePostCommand;
import com.practice.freeboard.application.port.in.usecase.CreatePostUseCase;
import com.practice.freeboard.application.port.in.usecase.ReadPostUseCase;
import com.practice.freeboard.domain.model.Post;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {

    private final CreatePostUseCase createPostUseCase;
    private final ReadPostUseCase readPostUseCase;

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

    @GetMapping("/{id}")
    public ResponseEntity<ReadPostResponseVo> read (@PathVariable Long id){

        Post post = readPostUseCase.read(id);

        ReadPostResponseVo readPostResponseVo = new ReadPostResponseVo(post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthorName());

        return ResponseEntity.ok(readPostResponseVo);
    }

}
