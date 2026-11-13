package com.practice.freeboard.adapter.in.web.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 매칭 요청 Body.
 * ownerId 는 Body에 안 넣음 → JWT(SecurityContext)에서 꺼냄.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMatchRequestVo {

    @NotBlank
    @Size(max = 50)
    private String symptom;

    @NotBlank
    @Size(max = 50)
    private String consultType;

    @NotBlank
    @Size(max = 50)
    private String petName;

    @NotBlank
    @Size(max = 50)
    private String petBreed;

    @NotBlank
    @Size(max = 30)
    private String petAge;

    @NotBlank
    @Size(max = 30)
    private String petWeight;

    @NotBlank
    @Size(max = 20)
    private String petGender;
}
