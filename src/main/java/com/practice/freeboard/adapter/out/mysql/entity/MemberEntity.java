package com.practice.freeboard.adapter.out.mysql.entity;

import com.practice.freeboard.domain.model.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 회원 테이블 매핑.
 * 왜 필요하냐면: 로그인할 때 DB에서 이메일로 회원을 찾고,
 * 저장된 해시 비번과 입력 비번을 비교해야 하기 때문.
 * (JWT는 그 검증이 끝난 뒤에 발급한다)
 *
 * matching_available: 수의사 라디오 ON/OFF 저장 칸.
 * ddl-auto=update 면 기동 시 컬럼 추가됨.
 */
@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor // JPA가 조회할 때 빈 생성자 필요
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB 자동증가 PK
    private Long id;

    /**
     * 로그인 키.
     * unique: 같은 이메일로 중복 가입 막기
     */
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    /**
     * BCrypt로 해시된 비밀번호 저장 칸.
     * 평문을 넣으면 안 된다. 회원가입 UseCase에서 encode 한 뒤 저장.
     */
    @Column(nullable = false, length = 100)
    private String password;

    /**
     * 역할.
     * EnumType.STRING: DB에 USER/VET/ADMIN 글자로 저장 (숫자 코드보다 읽기 쉬움)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    /**
     * 실시간 상담(매칭) 가능 여부.
     * DB 컬럼명 matching_available.
     * 가입 기본 false. 수의사가 라디오로 바꿀 때 UPDATE.
     */
    @Column(name = "matching_available", nullable = false)
    private boolean matchingAvailable;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * 회원가입·저장용 생성자.
     * id는 null로 두고 INSERT 때 DB가 채운다.
     */
    public MemberEntity(
            String email,
            String password,
            Role role,
            boolean matchingAvailable,
            LocalDateTime createdAt
    ) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.matchingAvailable = matchingAvailable;
        this.createdAt = createdAt;
    }
}
