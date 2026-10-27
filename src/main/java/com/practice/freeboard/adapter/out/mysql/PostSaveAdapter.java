package com.practice.freeboard.adapter.out.mysql;

import com.practice.freeboard.adapter.out.mysql.entity.PostEntity;
import com.practice.freeboard.adapter.out.mysql.mapper.PostEntityMapper;
import com.practice.freeboard.adapter.out.mysql.repository.PostJpaRepository;
import com.practice.freeboard.application.port.out.PostSavePort;
import com.practice.freeboard.domain.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostSaveAdapter implements PostSavePort {

    // DB 저장용 JPA
    private final PostJpaRepository postJpaRepository;
    // Post(domain) <-> PostEntity 변환
    private final PostEntityMapper postEntityMapper;

    // 생성자 주입 (Spring이 Bean 넣어 줌)
    public PostSaveAdapter(PostJpaRepository postJpaRepository,
                           PostEntityMapper postEntityMapper) {
        this.postJpaRepository = postJpaRepository;
        this.postEntityMapper = postEntityMapper;
    }

    @Override
    public Post save(Post post) {
        // 1) 도메인 -> DB용 엔터티
        PostEntity entity = postEntityMapper.toEntity(post);

        // 2) Insert (id가 없으면 새로 채번됨)
        PostEntity saved = postJpaRepository.save(entity);

        // 3) 저장된 엔터티 -> 도메인 (id 포함해서 위로 올림)
        return postEntityMapper.toDomain(saved);

    }


}
 