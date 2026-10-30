package com.practice.freeboard.application.port.in.usecase;

import com.practice.freeboard.domain.model.Post;

public interface ReadPostUseCase {

    Post read(Long id);

}
