package com.practice.freeboard.application.port.out;

import com.practice.freeboard.domain.model.Post;

import java.util.Optional;

public interface PostLoadPort {

    // DB에서 id로 찾아 도메인 Post로 돌려줌 (없으면 empty)
    Optional<Post> findById (Long id);

}
