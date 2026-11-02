package com.practice.freeboard.application.port.in.usecase;

import com.practice.freeboard.application.port.in.command.UpdatePostCommand;

public interface UpdatePostUseCase {

    // 데이터 수정이라 반환값 없음. Create 때 Long 타입 id를 반환하는 이유는 새로운 id 값을 프론트에 보내기 위함임.
    void update(UpdatePostCommand updatePostCommand);

}
