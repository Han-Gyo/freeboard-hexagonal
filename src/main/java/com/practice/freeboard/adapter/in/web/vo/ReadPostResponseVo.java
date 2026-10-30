package com.practice.freeboard.adapter.in.web.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReadPostResponseVo {

    private Long id;
    private String title;
    private String content;
    private String authorName;

}
