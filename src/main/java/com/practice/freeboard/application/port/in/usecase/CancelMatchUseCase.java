package com.practice.freeboard.application.port.in.usecase;

import com.practice.freeboard.application.port.in.command.CancelMatchCommand;

public interface CancelMatchUseCase {

    void cancelMatch(CancelMatchCommand command);
}
