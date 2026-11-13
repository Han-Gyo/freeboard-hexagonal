package com.practice.freeboard.domain.model;

import com.practice.freeboard.domain.exception.InvalidMatchException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 매칭 요청 도메인.
 * 보호자가 "수의사 찾아줘" 할 때 같이 보내는 한 건의 데이터.
 * 선착순 수락은 나중에 DB: UPDATE ... WHERE status=REQUESTED 로 막는다.
 */
@Getter
@AllArgsConstructor
public class MatchRequest {

    /** 매칭 요청 PK */
    private Long id;

    /** 요청한 보호자(회원) id — pet owner */
    private Long ownerId;

    /** 수락한 수의사 id. 대기(REQUESTED)일 때는 null */
    private Long acceptedVetId;

    /** 이 매칭 건의 진행 상태 */
    private MatchStatus status;

    // --- 보호자가 요청 시 보내는 상담·반려동물 정보 (뷰 기획 기준) ---

    /** 증상 분류. 예: 호흡기 */
    private String symptom;

    /** 상담 방식. 예: 화상+채팅 */
    private String consultType;

    /** 반려동물 이름 */
    private String petName;

    /** 종류/품종. 예: 말티즈 */
    private String petBreed;

    /** 나이 (화면 입력 그대로. 단위는 나중에 통일) */
    private String petAge;

    /** 체중 (화면 입력 그대로) */
    private String petWeight;

    /** 성별. 예: 남 / 여 */
    private String petGender;

    /** 요청 시각 */
    private LocalDateTime createdAt;

    /**
     * 새 매칭 요청 만들기.
     * 상태는 항상 REQUESTED, 수의사 id는 비움.
     */
    public static MatchRequest create(
            Long ownerId,
            String symptom,
            String consultType,
            String petName,
            String petBreed,
            String petAge,
            String petWeight,
            String petGender,
            LocalDateTime now
    ) {
        if (ownerId == null) {
            throw new InvalidMatchException("보호자(owner) id가 필요합니다.");
        }
        return new MatchRequest(
                null,
                ownerId,
                null,
                MatchStatus.REQUESTED,
                symptom,
                consultType,
                petName,
                petBreed,
                petAge,
                petWeight,
                petGender,
                now
        );
    }

    /**
     * 수의사가 이 요청을 수락.
     * REQUESTED 일 때만 가능.
     */
    public void accept(Long vetId) {
        if (vetId == null) {
            throw new InvalidMatchException("수의사 id가 필요합니다.");
        }
        if (this.status != MatchStatus.REQUESTED) {
            throw new InvalidMatchException("요청 상태에서만 수락할 수 있습니다.");
        }
        this.acceptedVetId = vetId;
        this.status = MatchStatus.ACCEPTED;
    }

    /**
     * 수의사(또는 시스템) 거절.
     * REQUESTED 일 때만 가능.
     */
    public void reject() {
        if (this.status != MatchStatus.REQUESTED) {
            throw new InvalidMatchException("요청 상태에서만 거절할 수 있습니다.");
        }
        this.status = MatchStatus.REJECTED;
    }

    /**
     * 요청 취소 (보호자 등).
     * REQUESTED 일 때만 가능.
     */
    public void cancel() {
        if (this.status != MatchStatus.REQUESTED) {
            throw new InvalidMatchException("요청 상태에서만 취소할 수 있습니다.");
        }
        this.status = MatchStatus.CANCELLED;
    }

    /**
     * 상담 완료 → 이 매칭 건 종료.
     * ACCEPTED 일 때만 가능.
     * 누가 눌러도 되는지는 Service에서 owner/vet 검사. (수의사 ON/OFF는 별도)
     */
    public void complete() {
        if (this.status != MatchStatus.ACCEPTED) {
            throw new InvalidMatchException("수락된 요청만 완료할 수 있습니다.");
        }
        this.status = MatchStatus.COMPLETED;
    }
}
