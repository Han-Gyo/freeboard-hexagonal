package com.practice.freeboard.adapter.in.web.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor  // AllArgsConstructor만 쓰게 되면 값을 못 넣는경우도 생김
@AllArgsConstructor
public class CreatePostRequestVo {

    private String title;
    private String content;
    private String authorName;

}
