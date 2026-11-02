package com.practice.freeboard.application.service;

import com.practice.freeboard.application.exception.PostNotFoundException;
import com.practice.freeboard.application.port.in.command.UpdatePostCommand;
import com.practice.freeboard.application.port.in.usecase.UpdatePostUseCase;
import com.practice.freeboard.application.port.out.ReadPostPort;
import com.practice.freeboard.application.port.out.UpdatePostPort;
import com.practice.freeboard.domain.model.Post;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdatePostService implements UpdatePostUseCase {

    // 조회 Port 재사용
    private final ReadPostPort readPostPort ;
    // 업데이트 Port
    private final UpdatePostPort updatePostPort;


    @Override
    public void update(UpdatePostCommand updatePostCommand) {

        // 사용자가 update 요청한 게시글이 있는지 조회하기 ( readPostPort 재사용 )
        Post exist = readPostPort.read(updatePostCommand.getId())
                .orElseThrow(() -> new PostNotFoundException(updatePostCommand.getId()));

        // 가져온 id 값 + 클라이언트가 요청한 데이터들 domain으로 변경
        Post updatePost = new Post (exist.getId(),
                updatePostCommand.getTitle(),
                updatePostCommand.getContent(),
                exist.getAuthorName(),
                exist.getCreatedAt());

        // domain에 담은 내용들 업데이트하는 출구로 보내기
        updatePostPort.update(updatePost);

    }
}
