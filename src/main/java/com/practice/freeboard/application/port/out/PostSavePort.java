package com.practice.freeboard.application.port.out;

import com.practice.freeboard.domain.model.Post;


public interface PostSavePort {

    // interface = public abstract이 기본이라서 생략가능.
    Post save (Post post);

}