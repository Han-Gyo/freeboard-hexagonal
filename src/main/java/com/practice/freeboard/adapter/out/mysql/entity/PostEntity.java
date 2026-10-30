package com.practice.freeboard.adapter.out.mysql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity                 // JPA가 관리하는 테이믈 매핑 클래스
@Table(name = "Post")   // 테이블 이름
@Getter
@NoArgsConstructor      // JPA용 빈 생성자
@AllArgsConstructor
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 id값 자동으로 부여
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @Column(nullable = false, length = 50)
    private String authorName;

    @Column(nullable = false)
    private LocalDateTime createdAt;

}
