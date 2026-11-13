package com.practice.freeboard.adapter.in.web.security;

import com.practice.freeboard.domain.model.Role;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * 요청마다 JWT를 검사하는 필터.
 * 왜 필요하냐면: 세션이 없으니까, 매 요청 Header의 Bearer 토큰으로
 * "누구인지 / 역할이 뭔지"를 다시 채워 줘야 Security가 인가할 수 있다.
 *
 * OncePerRequestFilter: 한 요청에 이 필터가 두 번 안 돌게 보장.
 */
@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /** Authorization 헤더 이름 */
    private static final String AUTHORIZATION_HEADER = "Authorization";

    /** Bearer 스킴 prefix. 예: "Bearer eyJhbGciOi..." */
    private static final String BEARER_PREFIX = "Bearer ";

    /** 토큰 파싱·검증기 (시크릿으로 도장 확인) */
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 실제 필터 로직.
     * 1) 헤더에서 토큰 꺼냄
     * 2) 유효하면 SecurityContext에 인증 정보 저장
     * 3) 다음 필터/컨트롤러로 요청 전달
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Header에서 Bearer 토큰만 추출. 없으면 null
        String token = resolveToken(request);

        // 토큰이 있을 때만 검증. 없으면 익명으로 다음 단계 진행
        // (공개 URL은 permitAll, 보호 URL은 아래에서 인증 없어 401)
        if (token != null) {
            try {
                // 서명·만료 확인 후 회원 id / 역할 읽기
                Long memberId = jwtTokenProvider.getMemberId(token);
                Role role = jwtTokenProvider.getRole(token);

                // 스프링 시큐리티가 알아먹는 권한 이름. hasRole("USER") <-> ROLE_USER
                SimpleGrantedAuthority authority =
                        new SimpleGrantedAuthority("ROLE_" + role.name());

                // 인증 객체 생성.
                // principal = 회원 id, credentials = null(이미 토큰으로 인증됨), authorities = 역할
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                memberId,
                                null,
                                List.of(authority)
                        );

                // 이 요청 스레드의 SecurityContext에 "로그인된 상태"로 저장
                // 이후 SecurityFilterChain 인가 규칙 / @PreAuthorize 가 이걸 본다
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtException | IllegalArgumentException e) {
                // 위조·만료·깨진 토큰이면 인증 정보 비움
                // 보호된 API는 체인에서 401로 막힌다
                SecurityContextHolder.clearContext();
            }
        }

        // 다음 필터로 넘겨야 요청이 컨트롤러까지 간다. 안 부르면 요청이 여기서 멈춤
        filterChain.doFilter(request, response);
    }

    /**
     * Authorization 헤더에서 Bearer 뒤 JWT 문자열만 잘라 낸다.
     * 형식이 아니면 null → 토큰 없는 요청으로 취급.
     */
    private String resolveToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);

        // "Bearer " 로 시작할 때만 토큰으로 인정
        if (header != null && header.startsWith(BEARER_PREFIX)) {
            return header.substring(BEARER_PREFIX.length()).trim();
        }
        return null;
    }
}
