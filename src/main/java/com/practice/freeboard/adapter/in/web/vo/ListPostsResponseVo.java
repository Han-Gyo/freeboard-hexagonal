package com.practice.freeboard.adapter.in.web.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

// 게시글 목록 API 응답 ( Web Adapter )
// 앱단에서 가공된 결과 데이터를 프론트가 쓰기 좋은 형식인 JSON형식으로 바꿔서 응답함
@Getter
@AllArgsConstructor
public class ListPostsResponseVo {

    // 현재 페이지 게시글들
    private final List<ReadPostResponseVo> posts;

    // 전체 게시글 수
    private final long totalElements;

    // 전체 페이지 수
    private final int totalPages;

    // 현재 페이지
    private final int page;

    // 페이지 크기
    private final int size;

}
