package com.practice.freeboard.application.port.out;

import com.practice.freeboard.domain.model.Post;

import java.util.Optional;

public interface ReadOnePostPort {

    Optional<Post> getById(Long id);

}
