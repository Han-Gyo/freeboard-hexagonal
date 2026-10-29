package com.practice.freeboard.application.port.in;

import com.practice.freeboard.application.port.in.dto.UpdatePostCommand;

public interface UpdatePostUseCase {

    // 업데이트는 수정하고 저장하는거기 때문에 클라이언트한테 보여줄 값은 없음 성공여부만 보여주면 됨. 따라서 리턴 없음
    void update (UpdatePostCommand updatePostCommand);

}
