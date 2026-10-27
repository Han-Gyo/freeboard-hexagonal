package com.practice.freeboard.application.port.out;

import com.practice.freeboard.domain.model.Post;

public interface CreatePostPort {

    // 도메인에 채운 값을 DB에 저장 자동으로 만들어진 id값을 서비스에서 꺼내서 Long타입으로 반환하면 됨
    Post save (Post post);

}
