package com.practice.freeboard.application.port.in.command;

// 클라이언트가 원하는 페이지 조건 ( page, size, sort )

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ListPostsQuery {

    // 페이지 번호 0부터
    private final int page;

    // 한 페이지 개수
    private final int size;

    // 정렬 조건 문자열 예 : "createdAt,desc(내림차순)" - 처음엔 문자열로 단순하게
    private final String sort;

}