package com.practice.freeboard.application.port.out;

import com.practice.freeboard.domain.model.Post;

public interface UpdatePostPort {

    // 수정된 게시글을 DB로 보내는 출구 ( Service가 domain에 데이터 담아서 넘김 )
    void update (Post post);

}
