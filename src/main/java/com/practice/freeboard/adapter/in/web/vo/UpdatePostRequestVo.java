package com.practice.freeboard.adapter.in.web.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostRequestVo {

    // 컨트롤러에서 객체 생성하고 사용자가 요청한 데이터 값을 넣어둠
    private String title;
    private String content;

}
