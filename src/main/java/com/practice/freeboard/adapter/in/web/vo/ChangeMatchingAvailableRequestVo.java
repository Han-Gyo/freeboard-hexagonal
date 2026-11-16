package com.practice.freeboard.adapter.in.web.vo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상담 가능 ON/OFF Body.
 * 라디오 true/false 만 받음. memberId 는 JWT에서.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeMatchingAvailableRequestVo {

    /** true=ON, false=OFF. null 이면 @NotNull 로 400 */
    @NotNull
    private Boolean available;
}
