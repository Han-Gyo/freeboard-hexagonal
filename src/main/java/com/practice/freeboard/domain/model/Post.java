package com.practice.freeboard.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor // 원래의 헥사고날은 순수 자바 코드만 사용하지만 Getter, 생성자 까지는 편의상 쓰는 곳 많음.
public class Post {

    private Long id ;
    private String title;
    private String content;
    private String authorName;
    private LocalDateTime createdAt;

}
