package com.practice.freeboard.adapter.in.web;

import com.practice.freeboard.adapter.in.web.vo.CreatePostRequestVo;
import com.practice.freeboard.adapter.in.web.vo.ListPostsResponseVo;
import com.practice.freeboard.adapter.in.web.vo.ReadPostResponseVo;
import com.practice.freeboard.adapter.in.web.vo.UpdatePostRequestVo;
import com.practice.freeboard.application.port.in.command.CreatePostCommand;
import com.practice.freeboard.application.port.in.command.ListPostsQuery;
import com.practice.freeboard.application.port.in.command.UpdatePostCommand;
import com.practice.freeboard.application.port.in.result.ListPostsResult;
import com.practice.freeboard.application.port.in.usecase.*;
import com.practice.freeboard.domain.model.Post;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {

    private final CreatePostUseCase createPostUseCase;
    private final ReadPostUseCase readPostUseCase;
    private final UpdatePostUseCase updatePostUseCase;
    private final DeletePostUseCase deletePostUseCase;
    private final ListPostsUseCase listPostsUseCase;

    @PostMapping
    public ResponseEntity<Map<String, Long>> createPost (@RequestBody @Valid CreatePostRequestVo createPostRequestVo){

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

    @GetMapping
    public ResponseEntity<ListPostsResponseVo> list(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestParam(defaultValue = "createdAt,DESC") String sort ){

        // 1. Web 파라미터 -> Application Query  (ListPostsQuery클래스)
        ListPostsQuery query = new ListPostsQuery(page, size, sort);

        // UseCase 실행 후 결과 ListPostsResult에 담기
        ListPostsResult result = listPostsUseCase.list(query);

        // ListPostsResult 담은걸 -> 응답 Vo로 매핑 Result는 도메인 타입으로 담겨있음
        List<ReadPostResponseVo> posts = result.getPosts().stream()
                .map(post -> new ReadPostResponseVo(
                        post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getAuthorName()))
                .toList();

        // List에 담은 게시글들 ListPostsResponseVo에 넣기
        ListPostsResponseVo body = new ListPostsResponseVo(posts,
                result.getTotalElements(),
                result.getTotalPages(),
                result.getPage(),
                result.getSize());

        return ResponseEntity.ok(body);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> update(@PathVariable Long id,
                                                      @RequestBody @Valid UpdatePostRequestVo updatePostRequestVo){

        // 1. Vo -> DTO
        UpdatePostCommand updateDto = new UpdatePostCommand(id, updatePostRequestVo.getTitle(),
                updatePostRequestVo.getContent());

        // 2. UseCase 가기
        updatePostUseCase.update(updateDto);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "수정 완료"));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete (@PathVariable Long id){

        // 담을 그릇 즉 DTO 만들 필요없이 바로 useCase가면 됨
        deletePostUseCase.delete(id);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "삭제 완료"));

    }


}
