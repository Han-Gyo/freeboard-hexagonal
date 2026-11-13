package com.practice.freeboard.domain.model;

import com.practice.freeboard.domain.exception.InvalidPostException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor // 원래의 헥사고날은 순수 자바 코드만 사용하지만 Getter, 생성자 까지는 편의상 씀.
public class Post {

    private Long id ;
    private String title;
    private String content;
    private String authorName;
    private LocalDateTime createdAt;

    // 도메인 규칙 : 금지어 있으면 저장 전에 막기
    public void validate() {
        validateNoBannedWord(title);
        validateNoBannedWord(content);
    }

    // title, content 각각에 대해 금지어 포함 여부 검사
    // private: 도메인 내부에서만 쓰는 헬퍼 (밖에서는 validate()만 호출)
    private void validateNoBannedWord(String text) {

        if (text == null) {
            return; // 빈값/ 형식은 Vo에있는 @NotBlank가 담
        }
        if (text.contains("광고") || text.contains("도배")) {
            throw new InvalidPostException("금지어가 포함되어 있습니다.");
        }

    }

}
