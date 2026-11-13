package com.practice.freeboard.domain.model;

/**
 * 매칭 요청 한 건의 진행 상태.
 * (수의사 ON/OFF 상담가능 여부와는 별개)
 */
public enum MatchStatus {

    /** 보호자가 요청함. 수의사 수락/거절 대기 */
    REQUESTED,

    /** 수의사가 수락함. 상담 진행 중 */
    ACCEPTED,

    /** 수의사(또는 시스템)가 거절함 */
    REJECTED,

    /** 보호자 등이 대기 중 요청을 취소함 */
    CANCELLED,

    /** 상담이 끝나 이 매칭 건이 종료됨 */
    COMPLETED
}
