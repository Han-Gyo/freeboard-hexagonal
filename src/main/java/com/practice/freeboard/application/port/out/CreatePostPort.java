package com.practice.freeboard.application.port.out;

import com.practice.freeboard.domain.model.Post;

// 게시글을 DB에 저장하는 출구 ( 출구의 설계는 adapter에서. )
public interface CreatePostPort {

    Post createPost (Post post);

}
