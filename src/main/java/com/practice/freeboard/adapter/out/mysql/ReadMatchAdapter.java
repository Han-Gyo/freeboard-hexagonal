package com.practice.freeboard.adapter.out.mysql;

import com.practice.freeboard.adapter.out.mysql.mapper.MatchRequestMapper;
import com.practice.freeboard.adapter.out.mysql.repository.MatchRequestJpaRepository;
import com.practice.freeboard.application.port.out.ReadMatchPort;
import com.practice.freeboard.domain.model.MatchRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ReadMatchAdapter implements ReadMatchPort {

    private final MatchRequestJpaRepository matchRequestJpaRepository;
    private final MatchRequestMapper matchRequestMapper;

    @Override
    public Optional<MatchRequest> read(Long matchId) {
        return matchRequestJpaRepository.findById(matchId)
                .map(matchRequestMapper::toDomain);
    }
}
