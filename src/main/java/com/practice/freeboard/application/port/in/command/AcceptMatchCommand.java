package com.practice.freeboard.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** 수의사가 매칭 건을 수락할 때 */
@Getter
@AllArgsConstructor
public class AcceptMatchCommand {

    private Long matchId;
    private Long vetId;
}
