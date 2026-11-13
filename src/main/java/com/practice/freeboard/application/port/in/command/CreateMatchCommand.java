package com.practice.freeboard.application.port.in.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 보호자 매칭 요청 입력.
 * 컨트롤러 Vo → 이 Command → Service.
 */
@Getter
@AllArgsConstructor
public class CreateMatchCommand {

    private Long ownerId;
    private String symptom;
    private String consultType;
    private String petName;
    private String petBreed;
    private String petAge;
    private String petWeight;
    private String petGender;
}
