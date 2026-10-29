package com.practice.freeboard.adapter.out.mysql;

import com.practice.freeboard.adapter.out.mysql.entity.PostEntity;
import com.practice.freeboard.adapter.out.mysql.mapper.PostEntityMapper;
import com.practice.freeboard.adapter.out.mysql.repository.PostJpaRepository;
import com.practice.freeboard.application.port.out.UpdatePostPort;
import com.practice.freeboard.domain.model.Post;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UpdatePostAdapter implements UpdatePostPort {

    // jpa
    private PostJpaRepository postJpaRepository;

    // mapper domain -> entity / entity -> domain 되어야 하니깐
    private PostEntityMapper postEntityMapper;

    @Override
    public void update(Post post) {

        // 서비스에서 가공 된 값이 저장 되어있는 도메인을 엔터티로 바꾸고 변수에 담기
        // update는 반환 타입이 void고 반환 필요없어서 다시 도메인으로 바꾸는 로직은 필요없음
        PostEntity entity = postEntityMapper.toEntity(post);

        // 엔터티로 바꾼 변수를 jpa로 DB에 업데이트(저장)하기
        postJpaRepository.save(entity);

    }
}
