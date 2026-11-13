package com.practice.freeboard.application.port.in.usecase;

import com.practice.freeboard.application.port.in.command.RejectMatchCommand;

public interface RejectMatchUseCase {

    void rejectMatch(RejectMatchCommand command);
}
