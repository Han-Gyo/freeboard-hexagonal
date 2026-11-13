package com.practice.freeboard.adapter.out.mysql;

import com.practice.freeboard.adapter.out.mysql.entity.PostEntity;
import com.practice.freeboard.adapter.out.mysql.mapper.PostMapper;
import com.practice.freeboard.adapter.out.mysql.repository.PostJpaRepository;
import com.practice.freeboard.application.port.out.CreatePostPort;
import com.practice.freeboard.domain.model.Post;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreatePostAdapter implements CreatePostPort {

    // Jpa 객체 생성
    private final PostJpaRepository postJpaRepository;

    // mapper 객체 생성 domain -> Entity로 매핑 후 DB 저장해야함
    private final PostMapper postMapper;


    @Override
    public Post createPost(Post post) {

        // domain -> Entity후 db 저장 그리고 해당 행위 saved 변수에 담기
        PostEntity saved = postJpaRepository.save(postMapper.toEntity(post));

        // DB에 저장된 Entity의 값(saved)을 domain 형식으로 반환
        return postMapper.toDomain(saved);
    }
}
