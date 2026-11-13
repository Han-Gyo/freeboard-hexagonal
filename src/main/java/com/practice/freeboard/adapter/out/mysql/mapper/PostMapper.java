package com.practice.freeboard.adapter.out.mysql.mapper;

import com.practice.freeboard.adapter.out.mysql.entity.PostEntity;
import com.practice.freeboard.domain.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    // domain -> entity로 변환
    public PostEntity toEntity (Post post) {
        return new PostEntity(post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthorName(),
                post.getCreatedAt()
                );
    }

    // entity -> domain으로 변환
    public Post toDomain (PostEntity postEntity){
        return new Post(postEntity.getId(),
                postEntity.getTitle(),
                postEntity.getContent(),
                postEntity.getAuthorName(),
                postEntity.getCreatedAt());
    }

}
