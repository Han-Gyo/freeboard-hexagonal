package com.practice.freeboard.adapter.out.mysql;

import com.practice.freeboard.adapter.out.mysql.mapper.PostEntityMapper;
import com.practice.freeboard.adapter.out.mysql.repository.PostJpaRepository;
import com.practice.freeboard.application.port.out.ReadOnePostPort;
import com.practice.freeboard.domain.model.Post;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ReadOnePostAdapter implements ReadOnePostPort {

    // DB 조회 해야하니깐 JPA레포 객체 생성
    private final PostJpaRepository postJpaRepository;

    // 조회하고 entity에 담긴 데이터 domain으로 체인지 해주기위해 Mapper 객체 생성
    private final PostEntityMapper postEntityMapper;

    @Override
    public Optional<Post> getById(Long id) {
        return postJpaRepository.findById(id).map(postEntityMapper :: toDomain) ;
    }
}
