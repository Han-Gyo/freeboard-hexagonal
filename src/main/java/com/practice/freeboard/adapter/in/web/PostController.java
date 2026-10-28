package com.practice.freeboard.adapter.in.web;

import com.practice.freeboard.adapter.in.web.vo.CreatePostRequestVo;
import com.practice.freeboard.application.port.in.CreatePostUseCase;
import com.practice.freeboard.application.port.in.dto.CreatePostCommand;
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

    // http에서 usecase만 호출
    private final CreatePostUseCase createPostUseCase;

    // 저장된 후 id 값을 프론트에 키, 벨류("메시지", id값) json형식으로 보낼거임
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


}
