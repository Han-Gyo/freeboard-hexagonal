package com.practice.freeboard.adapter.in.web;

import com.practice.freeboard.adapter.in.web.vo.CreatePostRequestVo;
import com.practice.freeboard.adapter.in.web.vo.PostResponseVo;
import com.practice.freeboard.adapter.in.web.vo.UpdatePostRequestVo;
import com.practice.freeboard.application.port.in.CreatePostUseCase;
import com.practice.freeboard.application.port.in.ReadPostUseCase;
import com.practice.freeboard.application.port.in.UpdatePostUseCase;
import com.practice.freeboard.application.port.in.dto.CreatePostCommand;
import com.practice.freeboard.application.port.in.dto.UpdatePostCommand;
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

    // http에서 usecase만 호출
    private final CreatePostUseCase createPostUseCase;

    private final ReadPostUseCase readPostUseCase;

    private final UpdatePostUseCase updatePostUseCase;

    // 저장된 후 id를 프론트에 키, 벨류("id", id의 값) json형식으로 보낼거임
    @PostMapping
    public ResponseEntity<Map<String, Long>>create(@RequestBody CreatePostRequestVo request){

        // 사용자가 요청한 데이터가 Vo에 담겨있는데 command로 담기
        CreatePostCommand command = new CreatePostCommand(
                request.getTitle(),
                request.getContent(),
                request.getAuthorName()
                );

        // 담은 dto를 서비스로 넘기기 (Usecase) -> 비즈니스 로직 시작 되고 DB에 저장까지 된 후 id값 생성된거 받기
        Long id = createPostUseCase.create(command);

        // DB에 저장될 때 생성된 id 값을 프론트한테줌
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", id)) ;

    }


    // Read_One 클라이언트가 요청한 id값의 데이터들 조회해서 Domain에 담았고 Vo로 변환해서 프론트에 넘김
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseVo> getPost(@PathVariable Long id){

        // UseCase에 Long타입의 id값을 넘겨주고 로직 수행한 후 결과 값을 도메인 타입인 post변수에 담음
        Post post = readPostUseCase.getById(id);

        // 도메인 타입의 값을 vo에 담음 객체생성 하면서 생성자 초기화 하기.
        PostResponseVo postResponseVo = new PostResponseVo(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthorName()
        );

        // 상태코드가 200 즉 정상이면 클라이언트한테 값을 담은 vo 객체를 보낸다.
        return ResponseEntity.ok(postResponseVo);

    }

    // update id는 PathVariable 나머지는 RequestBody에 넣어두기
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String> > postUpdate (@PathVariable Long id,
                        @RequestBody UpdatePostRequestVo updatePostRequestVo) {

        // 사용자한테 받은 데이터 Vo에 있는데 command 즉 dto에 담기
        UpdatePostCommand updatePostCommand = new UpdatePostCommand(
                id,
                updatePostRequestVo.getTitle(),
                updatePostRequestVo.getContent());

        // UseCase로 보내기 (비즈니스 로직 수행 해야하니깐) 객체생성 및 생성자는 전역변수로 처리해둠
        updatePostUseCase.update(updatePostCommand);


        return ResponseEntity.ok(Map.of("message", "수정 완료"));
    }


}
