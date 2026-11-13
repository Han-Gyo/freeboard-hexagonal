package com.practice.freeboard.adapter.out.mysql.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 댓글 테이블과 매핑되는 JPA 엔터티 ( 따라서 OUT.MYSQL.ENTITY 경로임 )
// POST : COMMENT = 1 : N 의 N( 여러 댓글 )
// N+1(목록 조회 후 Lazy로 댓글 추가 조회)을 재현·연습하기 위한 엔티티
@Entity
@Table(name = "Comment")
@NoArgsConstructor // JPA는 값을 넣기 위해서 기본 생성자 반드시 필요함
@Getter
public class CommentEntity {

    // PK를 DB가 오토 인크리먼트 해줌
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 댓글 본문
    @Column(nullable = false, length = 500)
    private String content;

    // 이 댓글이 달린 게시글 ( N : 1 관계 )
    // Lazy : 댓글만 조회할 때 Post를 즉시 로딩하지 않음
    // post_id 컬럼이 FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    // 생성용 생성자 ( id값은 DB가 부여 )
    public CommentEntity(String content, PostEntity post){
        this.content = content;
        this.post = post;
    }

}
