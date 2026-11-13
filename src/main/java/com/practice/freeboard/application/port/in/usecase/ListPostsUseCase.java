package com.practice.freeboard.application.port.in.usecase;

import com.practice.freeboard.application.port.in.command.ListPostsQuery;
import com.practice.freeboard.application.port.in.result.ListPostsResult;

// 게시글 목록 조회 유스케이스 (들어오는 Port)
public interface ListPostsUseCase {

    // 목록 조회 실행. 구현은 ListPostsService
    ListPostsResult list (ListPostsQuery query);

}