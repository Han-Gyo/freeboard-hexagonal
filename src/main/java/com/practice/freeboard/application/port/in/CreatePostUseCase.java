package com.practice.freeboard.application.port.in;

import com.practice.freeboard.application.port.in.dto.CreatePostCommand;

public interface CreatePostUseCase {

    Long create(CreatePostCommand command);

}
