package com.practice.freeboard.adapter.out.mysql;

import com.practice.freeboard.adapter.out.mysql.repository.PostJpaRepository;
import com.practice.freeboard.application.port.out.PostDeletePort;
import org.springframework.stereotype.Component;

@Component
public class PostDeleteAdapter implements PostDeletePort {

    private final PostJpaRepository postJpaRepository;

    public PostDeleteAdapter(PostJpaRepository postJpaRepository) {
        this.postJpaRepository = postJpaRepository;
    }

    @Override
    public void delete(Long id) {
        postJpaRepository.deleteById(id);
    }
}
