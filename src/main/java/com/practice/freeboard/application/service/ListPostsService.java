package com.practice.freeboard.application.service;

import com.practice.freeboard.application.port.in.command.ListPostsQuery;
import com.practice.freeboard.application.port.in.result.ListPostsResult;
import com.practice.freeboard.application.port.in.usecase.ListPostsUseCase;
import com.practice.freeboard.application.port.out.ListPostsPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 게시글 목록 조회 유스케이스 구현체, 페이징 조건(클라이언트한테 받은 Query)을 넘기고 결과를 그대로 반환함
@Service
@AllArgsConstructor
public class ListPostsService implements ListPostsUseCase {

    // 목록 조회 출구 (Port)
    private final ListPostsPort listPostsPort;

    // 목록 조회 - DB 에서 읽기만 하니깐 ReadOnly = true 설정하기
    @Override
    @Transactional(readOnly = true)
    public ListPostsResult list(ListPostsQuery query) {

        // 출구인 Port에 요청하고 결과값 컨트롤러에 반환
        return listPostsPort.listPosts(query);
    }
}
