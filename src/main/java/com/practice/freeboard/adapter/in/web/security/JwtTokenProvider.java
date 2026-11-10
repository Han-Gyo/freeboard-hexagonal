package com.practice.freeboard.adapter.in.web.security;

import com.practice.freeboard.domain.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT 발급·파싱 담당.
 * 왜 필요하냐면: 로그인 성공 후 "사원증(토큰)"을 만들어 주고,
 * 이후 요청에서는 헤더 Bearer 토큰을 열어 누구/역할인지 읽어야 하기 때문.
 *
 * 비밀키(yml jwt.secret)로 서명하니, 키를 모르는 사람은 위조 토큰을 못 만든다.
 */
@Component
public class JwtTokenProvider {

    /** 서명용 비밀키. yml의 jwt.secret을 바이트로 바꿔 HMAC 키로 씀 */
    private final SecretKey secretKey;

    /** 액세스 토큰 유효 시간(ms). yml jwt.access-token-validity-ms */
    private final long accessTokenValidityMs;

    /**
     * 생성자에서 yml 값을 주입받는다.
     * @Value: 설정 파일 값을 필드/파라미터에 넣는 스프링 기능
     */
    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-validity-ms}") long accessTokenValidityMs
    ) {
        // 문자열 시크릿 → JWT 라이브러리가 쓰는 SecretKey 객체
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidityMs = accessTokenValidityMs;
    }

    /**
     * 액세스 토큰 문자열 만들기.
     * subject = 회원 id, claim role = 권한.
     * 나중에 필터가 이 값을 읽어서 SecurityContext에 넣는다.
     */
    public String createAccessToken(Long memberId, Role role) {
        Date now = new Date();
        // 지금 + 유효시간 = 만료 시각
        Date expiry = new Date(now.getTime() + accessTokenValidityMs);

        return Jwts.builder()
                .subject(String.valueOf(memberId)) // 토큰 주인 (회원 PK)
                .claim("role", role.name())        // 인가에 쓸 역할
                .issuedAt(now)                     // 발급 시각
                .expiration(expiry)                // 만료 시각
                .signWith(secretKey)               // 서버 시크릿으로 도장
                .compact();                        // 최종 JWT 문자열
    }

    /**
     * Bearer로 온 토큰이 우리 서명이 맞는지 검증하고 payload를 연다.
     * 서명 위조·만료면 예외가 난다. (다음 단계 JWT 필터에서 사용)
     */
    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey) // 같은 시크릿으로 도장 확인
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /** 토큰에서 회원 id(subject) 꺼내기 */
    public Long getMemberId(String token) {

        return Long.valueOf(parseClaims(token).getSubject());
    }

    /** 토큰에서 역할 꺼내기 */
    public Role getRole(String token) {
        String roleName = parseClaims(token).get("role", String.class);
        return Role.valueOf(roleName);
    }
}
