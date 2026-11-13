package com.practice.freeboard.adapter.out.mysql.mapper;

import com.practice.freeboard.adapter.out.mysql.entity.MatchRequestEntity;
import com.practice.freeboard.domain.model.MatchRequest;
import org.springframework.stereotype.Component;

@Component
public class MatchRequestMapper {

    // domain → entity (DB 저장용)
    public MatchRequestEntity toEntity(MatchRequest match) {
        return new MatchRequestEntity(
                match.getId(),
                match.getOwnerId(),
                match.getAcceptedVetId(),
                match.getStatus(),
                match.getSymptom(),
                match.getConsultType(),
                match.getPetName(),
                match.getPetBreed(),
                match.getPetAge(),
                match.getPetWeight(),
                match.getPetGender(),
                match.getCreatedAt()
        );
    }

    // entity → domain (앱 로직용)
    public MatchRequest toDomain(MatchRequestEntity entity) {
        return new MatchRequest(
                entity.getId(),
                entity.getOwnerId(),
                entity.getAcceptedVetId(),
                entity.getStatus(),
                entity.getSymptom(),
                entity.getConsultType(),
                entity.getPetName(),
                entity.getPetBreed(),
                entity.getPetAge(),
                entity.getPetWeight(),
                entity.getPetGender(),
                entity.getCreatedAt()
        );
    }
}
