package com.practice.freeboard.adapter.in.web;

import com.practice.freeboard.adapter.in.web.vo.CreatePostRequestVo;
import com.practice.freeboard.adapter.in.web.vo.PostResponseVo;
import com.practice.freeboard.adapter.in.web.vo.UpdatePostRequestVo;
import com.practice.freeboard.application.port.in.CreatePostUseCase;
import com.practice.freeboard.application.port.in.GetPostUseCase;
import com.practice.freeboard.application.port.in.UpdatePostUseCase;
import com.practice.freeboard.application.port.in.dto.CreatePostCommand;
import com.practice.freeboard.application.port.in.dto.UpdatePostCommand;
import com.practice.freeboard.domain.model.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostController {

    // HTTP -> 유스케이스만 호출 ( DB 모름 )  클라이언트가 사용자가 할 수 있는것만 호출
    private final CreatePostUseCase createPostUseCase;

    private final GetPostUseCase getPostUseCase;

    private final UpdatePostUseCase updatePostUseCase;

    public PostController(CreatePostUseCase createPostUseCase,
                          GetPostUseCase getPostUseCase,
                          UpdatePostUseCase updatePostUseCase) {
        this.createPostUseCase = createPostUseCase;
        this.getPostUseCase = getPostUseCase;
        this.updatePostUseCase = updatePostUseCase;
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> create(@RequestBody
                                                    CreatePostRequestVo request) {
        // 1) 클라 요청 -> 어플리케이션 커맨드 ( CreatePostCommand )
        CreatePostCommand command = new CreatePostCommand(
                request.getTitle(),
                request.getContent(),
                request.getAuthorName()
        );

        // 2) 생성 유스케이스 실행 -> 저장된 id
        Long id = createPostUseCase.create(command);

        // 3) 201 created + { "id" : ... } 응답
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("id", id));

    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseVo> getPost(@PathVariable Long id) {
        // URL의 {id} -> Long id

        Post post = getPostUseCase.getById(id);

        // 도메인 -> 응답 VO
        PostResponseVo body = new PostResponseVo(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthorName()
        );

        return ResponseEntity.ok(body); // 200

    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> update(
            @PathVariable Long id,
            @RequestBody UpdatePostRequestVo request
            ) {

        UpdatePostCommand updatePostCommand = new UpdatePostCommand(
                id,
                request.getTitle(),
                request.getContent(),
                request.getAuthorName()
        );

        updatePostUseCase.update(updatePostCommand);

        return ResponseEntity.ok(Map.of("message", "수정 완료"));

    }


}
