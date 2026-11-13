package com.practice.freeboard.application.port.out;

import com.practice.freeboard.domain.model.Post;

import java.util.Optional;

public interface ReadPostPort {

    // 요청한 id가 있을 수도, 없을 수 있기 때문에 Optional 사용
    Optional<Post> read(Long id);

}
