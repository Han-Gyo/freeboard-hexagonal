package com.practice.freeboard.adapter.in.web.vo;

import com.practice.freeboard.domain.model.MatchStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 매칭 단건 조회 응답.
 * 도메인 MatchRequest 를 프론트에 내려줄 JSON 모양.
 */
@Getter
@AllArgsConstructor
public class ReadMatchResponseVo {

    private Long id;
    private Long ownerId;
    private Long acceptedVetId;
    private MatchStatus status;
    private String symptom;
    private String consultType;
    private String petName;
    private String petBreed;
    private String petAge;
    private String petWeight;
    private String petGender;
    private LocalDateTime createdAt;
}
