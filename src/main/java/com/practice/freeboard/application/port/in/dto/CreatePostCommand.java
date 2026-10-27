package com.practice.freeboard.application.port.in.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatePostCommand {

    private String title;
    private String content;
    private String authorName;


}
