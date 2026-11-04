package com.practice.freeboard.application.port.out;

import com.practice.freeboard.application.port.in.command.ListPostsQuery;
import com.practice.freeboard.application.port.in.result.ListPostsResult;

// 게시글 목록 조회 출구
public interface ListPostsPort {

    // page / size / sort 조건으로 DB에서 목록과 페이지 정보를 가져온다 구현체는 adapter.out에서
    ListPostsResult listPosts(ListPostsQuery query);

}
