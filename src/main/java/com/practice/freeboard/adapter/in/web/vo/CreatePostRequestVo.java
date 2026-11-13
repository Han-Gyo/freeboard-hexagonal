package com.practice.freeboard.adapter.in.web.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor  // AllArgsConstructor만 쓰게 되면 값을 못 넣는경우도 생김
@AllArgsConstructor
public class CreatePostRequestVo {

    // 형식을 입구에서 막아서 쓰레기 데이터가 Service, DB에 안 가게끔 앞에서 막음

    @NotBlank               // null, 빈문자, 공백 거절
    @Size(max = 200)        // 길이 제한
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    @Size(max = 50)
    private String authorName;

}
