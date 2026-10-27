package com.practice.freeboard.application.port.in;

import com.practice.freeboard.application.port.in.dto.UpdatePostCommand;

public interface UpdatePostUseCase {

    // 게시글 수정 - 성공 시 DB 저장만 하기 때문에 리턴 없음 void
    // 없으면 예외처리 서비스에서 PostNotFoundException
    void update(UpdatePostCommand command);

}
