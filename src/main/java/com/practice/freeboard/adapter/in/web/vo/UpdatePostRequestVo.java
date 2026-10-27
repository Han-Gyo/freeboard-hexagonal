package com.practice.freeboard.adapter.in.web.vo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePostRequestVo {

    private String title;
    private String content;
    private String authorName;


}
