package com.practice.freeboard.adapter.in.web.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostRequestVo {

    // private Long id; << C 단계 때는 DB가 오토 인크리먼트로 만들어줌
    private String title;
    private String content;
    private String authorName;

}
