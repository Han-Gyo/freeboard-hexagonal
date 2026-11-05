package com.practice.freeboard.adapter.out.mysql.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity                 // JPA가 관리하는 테이믈 매핑 클래스
@Table(name = "Post")   // 테이블 이름
@Getter
@NoArgsConstructor      // JPA용 빈 생성자
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

    // 1:N의 1쪽. mappedBy="post" → FK는 Comment.post_id가 가짐
    // LAZY: findAll()만 하면 comments는 안 가져옴 → getComments() 때 +N 가능
    // 해결은 여기 EAGER가 아니라, 조회 메서드(Fetch Join)에서 같이 로딩
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<CommentEntity> comments = new ArrayList<>();

    // 생성자 AllArgs 써도 됨 연습단계라 풀어서 씀
    public PostEntity (Long id,
                       String title,
                       String content,
                       String authorName,
                       LocalDateTime createdAt){
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorName = authorName;
        this.createdAt = createdAt;
    }

}
