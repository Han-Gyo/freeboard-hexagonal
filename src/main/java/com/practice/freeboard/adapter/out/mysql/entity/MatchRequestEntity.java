package com.practice.freeboard.adapter.out.mysql.entity;

import com.practice.freeboard.domain.model.MatchStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 매칭 요청 테이블.
 * 도메인 MatchRequest 를 DB 행으로 저장할 때 씀.
 */
@Entity
@Table(name = "match_request")
@Getter
@NoArgsConstructor
public class MatchRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 보호자(owner) 회원 id */
    @Column(nullable = false)
    private Long ownerId;

    /** 수락한 수의사 id. 대기 중이면 null */
    private Long acceptedVetId;

    /** 매칭 건 상태. DB에는 문자열로 저장 */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MatchStatus status;

    @Column(nullable = false, length = 50)
    private String symptom;

    @Column(nullable = false, length = 50)
    private String consultType;

    @Column(nullable = false, length = 50)
    private String petName;

    @Column(nullable = false, length = 50)
    private String petBreed;

    @Column(nullable = false, length = 30)
    private String petAge;

    @Column(nullable = false, length = 30)
    private String petWeight;

    @Column(nullable = false, length = 20)
    private String petGender;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public MatchRequestEntity(
            Long id,
            Long ownerId,
            Long acceptedVetId,
            MatchStatus status,
            String symptom,
            String consultType,
            String petName,
            String petBreed,
            String petAge,
            String petWeight,
            String petGender,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.ownerId = ownerId;
        this.acceptedVetId = acceptedVetId;
        this.status = status;
        this.symptom = symptom;
        this.consultType = consultType;
        this.petName = petName;
        this.petBreed = petBreed;
        this.petAge = petAge;
        this.petWeight = petWeight;
        this.petGender = petGender;
        this.createdAt = createdAt;
    }
}
