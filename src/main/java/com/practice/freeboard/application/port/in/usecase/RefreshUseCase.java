package com.practice.freeboard.application.port.in.usecase;

import com.practice.freeboard.application.port.in.command.RefreshCommand;
import com.practice.freeboard.application.port.in.result.RefreshResult;

/**
 * Access 재발급 유스케이스.
 * Refresh가 유효하면 새 Access(+ 새 Refresh)를 준다.
 */
public interface RefreshUseCase {

    RefreshResult refresh(RefreshCommand command);
}
