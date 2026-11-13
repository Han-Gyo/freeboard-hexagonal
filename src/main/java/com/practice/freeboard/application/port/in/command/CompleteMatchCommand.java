package com.practice.freeboard.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** 상담 종료 → 매칭 건 완료 (보호자·수의사 둘 다 가능) */
@Getter
@AllArgsConstructor
public class CompleteMatchCommand {

    private Long matchId;

    /** 종료 누른 회원 id (owner 또는 수락 vet) */
    private Long memberId;
}
