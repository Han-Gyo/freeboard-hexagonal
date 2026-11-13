package com.practice.freeboard.application.service;

import com.practice.freeboard.application.port.in.command.CreateMatchCommand;
import com.practice.freeboard.application.port.in.usecase.CreateMatchUseCase;
import com.practice.freeboard.application.port.out.CreateMatchPort;
import com.practice.freeboard.domain.model.MatchRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CreateMatchService implements CreateMatchUseCase {

    private final CreateMatchPort createMatchPort;

    @Override
    public Long createMatch(CreateMatchCommand command) {

        // Command → 도메인 (상태는 REQUESTED 로 생성)
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

        // 저장 출구로 보내고 id 받기
        MatchRequest saved = createMatchPort.createMatch(match);
        return saved.getId();
    }
}
