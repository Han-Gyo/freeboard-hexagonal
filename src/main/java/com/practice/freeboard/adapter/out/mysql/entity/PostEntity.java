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

    // 낙관적 락. 조회한 version과 DB version이 같을 때만 UPDATE 된다.
    // 새 글은 0으로 들어가고, 저장이 성공할 때마다 JPA가 1씩 올린다. 직접 더하지 않는다.
    @Version
    private Long version;

    // 1:N의 1쪽임. mappedBy="post" -> FK는 Comment.post_id가 가짐
    // LAZY: findAll()만 하면 comments는 안 가져옴 -> getComments() 때 +N 가능
    // 해결은 여기 EAGER가 아니라, 조회 메서드(Fetch Join)에서 같이 로딩
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<CommentEntity> comments = new ArrayList<>();

    // 새 글 저장용. version은 null로 두고, INSERT 때 JPA가 0을 넣는다.
    public PostEntity (Long id,
                       String title,
                       String content,
                       String authorName,
                       LocalDateTime createdAt){
        this(id, title, content, authorName, createdAt, null);
    }

    // 수정 저장용. 조회해 둔 version을 같이 넣어야 충돌 검사가 된다. 충돌 검사는 JPA가 자동으로 해줌.
    // 실 서비스에서도 NoArgs를 통해 빈 생성자를 만들고 JPA가 version의 값을 넣음
    public PostEntity (Long id,
                       String title,
                       String content,
                       String authorName,
                       LocalDateTime createdAt,
                       Long version){
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorName = authorName;
        this.createdAt = createdAt;
        this.version = version;
    }

}
