package com.practice.freeboard.application.port.in.usecase;

import com.practice.freeboard.application.port.in.command.ChangeMatchingAvailableCommand;

/** 수의사 매칭(상담) 가능 여부 변경 */
public interface ChangeMatchingAvailableUseCase {

    void changeMatchingAvailable(ChangeMatchingAvailableCommand command);
}
