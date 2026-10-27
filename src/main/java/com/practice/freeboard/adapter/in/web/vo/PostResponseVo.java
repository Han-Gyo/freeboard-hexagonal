package com.practice.freeboard.adapter.in.web.vo;


import lombok.AllArgsConstructor;
import lombok.Getter;

// 게시글 조회 응 JSON용 ( 프론트로 나감 )
@Getter
@AllArgsConstructor
public class PostResponseVo {

    private Long id;
    private String title;
    private String content;
    private String authorName;
    // 연습단계라서 postType, createdAt 등은 나중에 추가

}
