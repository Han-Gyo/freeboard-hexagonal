package com.practice.freeboard.application.port.in;

import com.practice.freeboard.domain.model.Post;

public interface GetPostUseCase {

    // id로 게시글 조회 -> 도메인 Post 변환
    Post getById (Long id);

}
