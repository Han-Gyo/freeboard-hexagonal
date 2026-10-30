package com.practice.freeboard.application.port.in.usecase;


import com.practice.freeboard.application.port.in.command.CreatePostCommand;

public interface CreatePostUseCase {

    // 프론트에 id값을 반환해야 하기 때문에 Long타입 파라미터는 dto
    Long createPost(CreatePostCommand createPostCommand);

}
