package com.practice.freeboard.adapter.out.mysql;

import com.practice.freeboard.adapter.out.mysql.mapper.PostEntityMapper;
import com.practice.freeboard.adapter.out.mysql.repository.PostJpaRepository;
import com.practice.freeboard.application.port.out.PostLoadPort;
import com.practice.freeboard.domain.model.Post;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PostLoadAdapter implements PostLoadPort {

    private final PostJpaRepository postJpaRepository;
    private final PostEntityMapper postEntityMapper;

    public PostLoadAdapter(PostJpaRepository postJpaRepository,
                           PostEntityMapper postEntityMapper) {
        this.postJpaRepository = postJpaRepository;
        this.postEntityMapper = postEntityMapper;
    }

    @Override
    public Optional<Post> findById(Long id) {
        // 1) DB에서 Entity 조회
        // 2) 있으면 Entity -> Domain Post로 매핑
        return postJpaRepository.findById(id).map(postEntityMapper::toDomain);
    }
}
