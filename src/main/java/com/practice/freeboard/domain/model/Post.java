package com.practice.freeboard.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Post {

    private Long id ;
    private String title;
    private String content;
    private String authorName;
    private PostType postType;
    private LocalDateTime createdAt;

}
