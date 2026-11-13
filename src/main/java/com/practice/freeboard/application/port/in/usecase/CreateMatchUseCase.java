package com.practice.freeboard.application.port.in.usecase;

import com.practice.freeboard.application.port.in.command.CreateMatchCommand;

public interface CreateMatchUseCase {

    /** 매칭 요청 생성 후 id 반환 */
    Long createMatch(CreateMatchCommand command);
}
