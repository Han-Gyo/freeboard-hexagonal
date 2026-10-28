package com.practice.freeboard.adapter.in.web.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 프론트에 응답할 Vo 그릇

@Getter
@AllArgsConstructor
public class PostResponseVo {

    private Long id;
    private String title;
    private String content;
    private String authorName;


}
