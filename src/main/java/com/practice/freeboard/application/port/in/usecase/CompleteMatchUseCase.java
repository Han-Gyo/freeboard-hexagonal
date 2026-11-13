package com.practice.freeboard.application.port.in.usecase;

import com.practice.freeboard.application.port.in.command.CompleteMatchCommand;

public interface CompleteMatchUseCase {

    void completeMatch(CompleteMatchCommand command);
}
