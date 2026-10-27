package com.practice.freeboard.application.port.in.dto;

//
public class CreatePostCommand {

    // 내용: title, content, authorName 필드 + 생성자/getter

    private String title;
    private String content;
    private String authorName;

    public CreatePostCommand(String title, String content, String authorName) {
        this.title = title;
        this.content = content;
        this.authorName = authorName;
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
}
