package com.practice.freeboard.application.service;

import com.practice.freeboard.application.exception.NoAvailableVetException;
import com.practice.freeboard.application.port.in.command.CreateMatchCommand;
import com.practice.freeboard.application.port.in.usecase.CreateMatchUseCase;
import com.practice.freeboard.application.port.out.CreateMatchPort;
import com.practice.freeboard.application.port.out.ExistsAvailableVetPort;
import com.practice.freeboard.domain.model.MatchRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 보호자 매칭 요청 생성.
 *
 * 입구 규칙: ON 수의사가 0명이면 요청 자체를 안 받음.
 * → 클라가 빨리 "지금은 없다"를 알고, DB에 REQUESTED 헛행도 안 쌓임.
 * ON 이 1명 이상이면 기존처럼 REQUESTED INSERT.
 */
@Service
@AllArgsConstructor
public class CreateMatchService implements CreateMatchUseCase {

    private final CreateMatchPort createMatchPort;
    private final ExistsAvailableVetPort existsAvailableVetPort;

    @Override
    public Long createMatch(CreateMatchCommand command) {

        // 0) 받을 수의사 있는지 먼저 확인 (입구 차단)
        if (!existsAvailableVetPort.existsAvailableVet()) {
            throw new NoAvailableVetException();
        }

        // 1) Command → 도메인 (상태는 REQUESTED)
        MatchRequest match = MatchRequest.create(
                command.getOwnerId(),
                command.getSymptom(),
                command.getConsultType(),
                command.getPetName(),
                command.getPetBreed(),
                command.getPetAge(),
                command.getPetWeight(),
                command.getPetGender(),
                LocalDateTime.now()
        );

        // 2) 저장 후 id 반환
        MatchRequest saved = createMatchPort.createMatch(match);
        return saved.getId();
    }
}
