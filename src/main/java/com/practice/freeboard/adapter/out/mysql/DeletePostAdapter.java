package com.practice.freeboard.adapter.out.mysql;

import com.practice.freeboard.adapter.out.mysql.repository.PostJpaRepository;
import com.practice.freeboard.application.port.out.DeletePostPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeletePostAdapter implements DeletePostPort {

    // Repo 객체 생성만, 여기서는 Mapper 필요 없음 반환 안 해도 됨
    private final PostJpaRepository postJpaRepository;

    @Override
    public void delete(Long id) {

        postJpaRepository.deleteById(id);

    }
}
