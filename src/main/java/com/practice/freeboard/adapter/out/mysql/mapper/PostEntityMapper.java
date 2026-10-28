package com.practice.freeboard.adapter.out.mysql.mapper;

import com.practice.freeboard.adapter.out.mysql.entity.PostEntity;
import com.practice.freeboard.domain.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostEntityMapper {

    // Domain -> Entity (DB 저장용)
    public PostEntity toEntity (Post post){

        PostEntity postEntity = new PostEntity(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthorName(),
                post.getPostType(),
                post.getCreatedAt()
                );

        return postEntity;
    }

    // Entity -> Domain ( 조회 || 저장 이후 리턴용 )
    public Post toDomain (PostEntity postEntity){

        Post post = new Post(
                postEntity.getId(),
                postEntity.getTitle(),
                postEntity.getContent(),
                postEntity.getAuthorName(),
                postEntity.getPostType(),
                postEntity.getCreatedAt()
        );

        return post;
    }

}
