package com.practice.freeboard.domain.model;

import java.time.LocalDateTime;


public class Post {

    // 공통 속성은 여기에 각각의 다른 속성(규칙)은 Policy.java에 작성하기
    // ex) 공지는 관리자만 작성, 1:1은 비공개, 자유게시판은 회원 작성 가능

    // 도메인 속성 값들
    private Long id;
    private String title;
    private String content;
    private String authorName;
    private PostType postType;
    private LocalDateTime createdAt;

    // 생성자
    public Post(Long id, String title, String content, String authorName, PostType postType,
                LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorName = authorName;
        this.postType = postType;
        this.createdAt = createdAt;
    }

    // getter
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthorName() {
        return authorName;
    }

    public PostType getPostType() {
        return postType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
