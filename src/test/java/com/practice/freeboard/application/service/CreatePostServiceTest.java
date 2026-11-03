package com.practice.freeboard.application.service;


import com.practice.freeboard.application.port.in.command.CreatePostCommand;
import com.practice.freeboard.application.port.out.CreatePostPort;
import com.practice.freeboard.domain.exception.InvalidPostException;
import com.practice.freeboard.domain.model.Post;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

// Mockito 쓰겠다고 JUnit에 알림 ( Spring 서버 안 켬 )
@ExtendWith(MockitoExtension.class)
class CreatePostServiceTest {

    @Mock           // 가짜 Port - 진짜 DB Adapter 대신
    private CreatePostPort createPostport;

    @InjectMocks    // 위의 Mock을 CreatePostService 생성자에 주입
    private CreatePostService createPostService;

    @Test
    void createPost_succeess_callsPortAndReturnsId(){
        // given - 준비
        CreatePostCommand command = new CreatePostCommand("제목", "내용", "작성자");

        Post saved = new Post(1L, "제목", "내용", "작성자", LocalDateTime.now());

        // Port가 무엇을 받든 saved를 돌려준다고 설정
        given(createPostport.createPost(any(Post.class))).willReturn(saved);

        // when - 실행
        Long id = createPostService.createPost(command);

        // then - 검증
        assertThat(id).isEqualTo(1L);
        // Port가 한 번 실행 됐는지 검증
        verify(createPostport).createPost(any(Post.class));
    }

    @Test
    void createPost_bannedWord_throwsAndDoesNotCallPort(){
        // given
        CreatePostCommand command = new CreatePostCommand("제목", "광고입니다", "작성자");

        // when / then
        assertThatThrownBy(() -> createPostService.createPost(command))
                .isInstanceOf(InvalidPostException.class)
                .hasMessage("금지어가 포함되어 있습니다.");

        verify(createPostport, never()).createPost(any(Post.class));    // DB 출구 안
    }

}
