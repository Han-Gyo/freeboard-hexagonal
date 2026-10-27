package com.practice.freeboard.adapter.out.mysql.mapper;

import com.practice.freeboard.adapter.out.mysql.entity.PostEntity;
import com.practice.freeboard.domain.model.Post;
import org.springframework.stereotype.Component;

// 도메인의 Post.java를 PostEntity로 중간에서 변환해주는 클래스
// 컴포넌트를 붙이면 나중에 어뎁터에서 주입 가능
@Component
public class PostEntityMapper {

    // 도메인 객체를 DB용 Entity로 변환
    public PostEntity toEntity (Post post) {

        return new PostEntity (
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthorName(),
                post.getPostType(),
                post.getCreatedAt()
        );

    }

    // Entity를 다시 도메인 객체로 변환
    public Post toDomain(PostEntity entity){

        return new Post(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getAuthorName(),
                entity.getPostType(),
                entity.getCreatedAt()
        );

    }

}
