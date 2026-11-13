package com.practice.freeboard.application.port.in.result;

import com.practice.freeboard.domain.model.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


// 목록 + 페이지 메타 (Spring Page 대신 Application 결과 타입)
@Getter
@AllArgsConstructor
public class ListPostsResult {

    private final List<Post> posts;
    private final long totalElements;   // 전체 글 수
    private final int totalPages;       // 전체 페이지 수
    private final int page;             // 현재 페이지
    private final int size;             // 요청 사이즈

}
