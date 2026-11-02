package com.practice.freeboard.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

// DTO 즉 자바에서 사용될 UseCase로 넘기는 입력 데이터 그릇
@Getter
@AllArgsConstructor
public class UpdatePostCommand {

    private Long id;
    private String title;
    private String content;

}
