package com.practice.freeboard.adapter.in.web.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor  // AllArgs만 있으면 기본 생성자가 없어 Jackson 바인딩이 실패할 수 있음
@AllArgsConstructor
public class UpdatePostRequestVo {

    private String title;
    private String content;

}
