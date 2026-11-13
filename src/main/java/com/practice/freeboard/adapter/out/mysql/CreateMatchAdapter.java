package com.practice.freeboard.adapter.out.mysql;

import com.practice.freeboard.adapter.out.mysql.entity.MatchRequestEntity;
import com.practice.freeboard.adapter.out.mysql.mapper.MatchRequestMapper;
import com.practice.freeboard.adapter.out.mysql.repository.MatchRequestJpaRepository;
import com.practice.freeboard.application.port.out.CreateMatchPort;
import com.practice.freeboard.domain.model.MatchRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreateMatchAdapter implements CreateMatchPort {

    private final MatchRequestJpaRepository matchRequestJpaRepository;
    private final MatchRequestMapper matchRequestMapper;

    @Override
    public MatchRequest createMatch(MatchRequest matchRequest) {

        // domain → entity → INSERT
        MatchRequestEntity saved = matchRequestJpaRepository.save(
                matchRequestMapper.toEntity(matchRequest)
        );

        // 저장된 행(id 포함)을 다시 domain 으로
        return matchRequestMapper.toDomain(saved);
    }
}
