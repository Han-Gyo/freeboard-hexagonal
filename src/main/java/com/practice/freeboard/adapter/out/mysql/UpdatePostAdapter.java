package com.practice.freeboard.adapter.out.mysql;

import com.practice.freeboard.adapter.out.mysql.entity.PostEntity;
import com.practice.freeboard.adapter.out.mysql.mapper.PostMapper;
import com.practice.freeboard.adapter.out.mysql.repository.PostJpaRepository;
import com.practice.freeboard.application.port.out.UpdatePostPort;
import com.practice.freeboard.domain.model.Post;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UpdatePostAdapter implements UpdatePostPort {

    // Jpa(Repo) 객체생성
    private final PostJpaRepository postJpaRepository ;
    // Mapper 객체생성
    private final PostMapper postMapper;

    @Override
    public void update(Post post) {

        // 1. Mapper로 Domain -> Entity로 바꾸기
        PostEntity mapperPost = postMapper.toEntity(post);

        // 2. DB 보내기
        postJpaRepository.save(mapperPost);

    }
}
