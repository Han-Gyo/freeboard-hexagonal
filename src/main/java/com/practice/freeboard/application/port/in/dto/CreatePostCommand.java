package com.practice.freeboard.application.port.in.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatePostCommand {

    // 내용: title, content, authorName 필드 + 생성자/getter

    private String title;
    private String content;
    private String authorName;


}
