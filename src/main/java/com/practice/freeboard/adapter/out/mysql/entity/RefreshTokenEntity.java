package com.practice.freeboard.adapter.out.mysql.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Refresh 토큰 저장 테이블.
 * Access는 JWT라 서버에 안 남겨도 되지만,
 * Refresh는 로그아웃·폐기하려면 DB에 있어야 "이 토큰 아직 쓸 수 있는지" 확인할 수 있다.
 */
@Entity
@Table(name = "refresh_token")
@Getter
@NoArgsConstructor
public class RefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 클라이언트에게 준 Refresh 문자열.
     * unique: 같은 토큰이 두 줄로 들어가면 안 됨.
     * 길이 여유 있게 (랜덤 문자열)
     */
    @Column(nullable = false, unique = true, length = 100)
    private String token;

    /**
     * 이 Refresh가 누구 것인지 (member.id).
     * 재발급할 때 회원 찾아서 새 Access 만들 때 씀.
     */
    @Column(nullable = false)
    private Long memberId;

    /** 이 시간 지나면 Refresh로도 재발급 불가 → 다시 로그인 */
    @Column(nullable = false)
    private LocalDateTime expiresAt;

    /**
     * 로그아웃 등으로 미리 죽인 토큰인지.
     * true면 만료 전이라도 사용 불가.
     */
    @Column(nullable = false)
    private boolean revoked;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * 로그인 성공 때 새 Refresh 저장용.
     * id는 DB가 채움. revoked는 처음엔 false.
     */
    public RefreshTokenEntity(
            String token,
            Long memberId,
            LocalDateTime expiresAt,
            LocalDateTime createdAt
    ) {
        this.token = token;
        this.memberId = memberId;
        this.expiresAt = expiresAt;
        this.revoked = false;
        this.createdAt = createdAt;
    }

    /** 로그아웃·토큰 회전 때 호출. 이후 이 토큰으로는 refresh 못 함 */
    public void revoke() {
        this.revoked = true;
    }

    /**
     * 지금 써도 되는 Refresh인지.
     * 폐기됐거나 만료면 false.
     */
    public boolean isUsable() {
        return !revoked && expiresAt.isAfter(LocalDateTime.now());
    }
}
