package com.practice.freeboard.application.port.in.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdatePostCommand {


    private Long id;
    private String title;
    private String content;

}
