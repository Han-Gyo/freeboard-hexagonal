package com.practice.freeboard.adapter.out.mysql;

import com.practice.freeboard.adapter.out.mysql.mapper.PostMapper;
import com.practice.freeboard.adapter.out.mysql.repository.PostJpaRepository;
import com.practice.freeboard.application.port.out.ReadPostPort;
import com.practice.freeboard.domain.model.Post;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ReadPostAdapter implements ReadPostPort {

    // JPA
    private final PostJpaRepository postJpaRepository;
    // Mapper entity를 domain타입으로 담기 위해
    private final PostMapper postMapper;

    @Override
    public Optional<Post> read(Long id) {

        // DB 조회 하러갔다가 id 값이 있으면 mapper로 도메인 타입으로 담기
        return postJpaRepository.findById(id).map(postMapper :: toDomain);
    }
}
