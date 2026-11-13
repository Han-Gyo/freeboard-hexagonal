package com.practice.freeboard.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** 보호자가 대기 중 요청을 취소할 때 */
@Getter
@AllArgsConstructor
public class CancelMatchCommand {

    private Long matchId;
    private Long ownerId;
}
