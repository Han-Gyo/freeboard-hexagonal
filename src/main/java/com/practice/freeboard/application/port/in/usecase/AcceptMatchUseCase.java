package com.practice.freeboard.application.port.in.usecase;

import com.practice.freeboard.application.port.in.command.AcceptMatchCommand;

public interface AcceptMatchUseCase {

    void acceptMatch(AcceptMatchCommand command);
}
