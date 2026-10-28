package com.practice.freeboard.adapter.out.mysql;

import com.practice.freeboard.adapter.out.mysql.entity.PostEntity;
import com.practice.freeboard.adapter.out.mysql.mapper.PostEntityMapper;
import com.practice.freeboard.adapter.out.mysql.repository.PostJpaRepository;
import com.practice.freeboard.application.port.out.CreatePostPort;
import com.practice.freeboard.domain.model.Post;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CreatePostAdapter implements CreatePostPort {

    private final PostJpaRepository postJpaRepository;
    private final PostEntityMapper postEntityMapper;


    @Override
    public Post save(Post post) {

        // 저장하기 위해서 Domain을 Entity로 변환
        PostEntity postEntity = postEntityMapper.toEntity(post);

        // DB 저장
        PostEntity saved = postJpaRepository.save(postEntity);

        // 서비스에서 ID 꺼내야 하니깐 id값 채운 도메인으로 다시 변환
        return postEntityMapper.toDomain(saved);
    }
}
