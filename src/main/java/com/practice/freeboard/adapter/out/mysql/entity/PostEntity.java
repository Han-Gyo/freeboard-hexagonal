package com.practice.freeboard.adapter.out.mysql.entity;

import com.practice.freeboard.domain.model.PostType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, length = 50)
    private String authorName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PostType postType;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected PostEntity(){
        // JPA 기본 생성자
    }

    public PostEntity(Long id, String title, String content, String authorName,
                      PostType postType, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorName = authorName;
        this.postType = postType;
        this.createdAt = createdAt;
    }

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
