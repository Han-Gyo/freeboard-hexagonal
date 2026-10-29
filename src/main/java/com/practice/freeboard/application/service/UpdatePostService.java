package com.practice.freeboard.application.service;

import com.practice.freeboard.application.exception.PostNotFoundException;
import com.practice.freeboard.application.port.in.UpdatePostUseCase;
import com.practice.freeboard.application.port.in.dto.UpdatePostCommand;
import com.practice.freeboard.application.port.out.ReadOnePostPort;
import com.practice.freeboard.application.port.out.UpdatePostPort;
import com.practice.freeboard.domain.model.Post;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UpdatePostService implements UpdatePostUseCase {

    private ReadOnePostPort readOnePostPort;
    private UpdatePostPort updatePostPort;

    @Override
    @Transactional // 조회하고, 요청한 데이터 업데이트까지 전부 한 묶음으로 완료 되어야함 하나라도 안 되면 롤백 해야하기 때문
    public void update(UpdatePostCommand updatePostCommand) {

        // 1. 기존에 저장 되어있는 게시글 조회해오기 id값 가져오기 위해서 (없으면 예외처리)
        Post existing = readOnePostPort.getById(updatePostCommand.getId())
                .orElseThrow(() -> new PostNotFoundException(updatePostCommand.getId()));

        // 2. domain 타입으로 바꿀 값 들 + 기존에 있던 값들 초기화 하기
        Post updated = new Post(
                existing.getId(),
                updatePostCommand.getTitle(),
                updatePostCommand.getContent(),
                existing.getAuthorName(),
                existing.getPostType(),
                existing.getCreatedAt()
        );

        // 3. 바꿀 값 넣은 도메인을 DB에 저장하기 -> port로 보내면 됨
        updatePostPort.update(updated);

    }
}
