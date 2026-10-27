package com.practice.freeboard.adapter.out.mysql.entity;

import com.practice.freeboard.domain.model.PostType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table (name = "posts")
@Getter
@NoArgsConstructor // JPA용 빈 생성
@AllArgsConstructor
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 id 자동생성 하게 해주는 어노테이션
    private Long id;

    @Column (nullable = false, length = 200)
    private String title;

    @Column (nullable = false, columnDefinition = "text")
    private String content;

    @Column (nullable = false, length = 50)
    private String authorName;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false, length = 20)
    private PostType postType;

    @Column (nullable = false)
    private LocalDateTime createdAt;


}
