package com.practice.freeboard.application.port.in;

import com.practice.freeboard.domain.model.Post;

public interface ReadPostUseCase {

    /* 사용자가 요청한 Long 타입의 id 값을 파라미터로 받고 비즈니스 로직 수행 후
     결과 값을 도메인 타입으로 담고 컨트롤러에 보냄 */
    Post getById(Long id);

}
