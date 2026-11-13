package com.practice.freeboard.adapter.in.web.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 스프링 시큐리티 환경설정 클래스.
 * 여기에 등록한 @Bean 리턴 객체를 스프링이 보관했다가 필요할 때 주입한다.
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    /**
     * 요청 Header의 JWT를 읽어 SecurityContext에 넣는 필터.
     * 필터 체인 앞쪽에 끼워 넣기 위해 주입받는다.
     */
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * 비밀번호 해시용 인코더.
     * BCrypt로 저장하고, 로그인 때는 matches()로 비교한다.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 스프링 빈으로 등록되는 객체 (설정값이 아니라 인코더 인스턴스)
        return new BCryptPasswordEncoder();
    }

    /**
     * 시큐리티 필터 체인.
     * 요청이 컨트롤러에 닿기 전에 "허용/차단" 규칙을 여기서 정한다.
     *
     * 6~7단계:
     * - 가입/로그인/스웨거: 공개
     * - DELETE /posts/** : ADMIN만 (역할 인가 연습)
     * - 그 외: 로그인(유효 JWT)만 되면 통과
     *
     * hasRole("ADMIN") 은 Context의 ROLE_ADMIN 권한을 본다.
     * (JWT 필터에서 "ROLE_" + role.name() 으로 넣어 둠)
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // REST API + JWT라서 브라우저 세션 CSRF는 끈다
                .csrf(csrf -> csrf.disable())
                // 세션에 로그인 상태 안 쌓음. 요청마다 JWT로 확인하는 쪽
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // URL별 인가 규칙 (위→아래 순서대로 매칭, 먼저 맞는 규칙 적용)
                .authorizeHttpRequests(auth -> auth
                        // 가입·로그인·재발급은 Access 없이 호출해야 함 재발급 때 Access필요 없는 이유는 어차피 리프레쉬 토큰으로 검증해서 재발급 하기 때문임. Access가 있든 말든 관계 X
                        .requestMatchers(
                                "/auth/signup",
                                "/auth/login",
                                "/auth/refresh",
                                "/auth/logout"
                        ).permitAll()
                        // API 문서 화면/스펙도 로그인 없이 보게
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()
                        // 게시글 삭제만 관리자 역할 필요 (7단계 역할 인가)
                        .requestMatchers(HttpMethod.DELETE, "/posts/**").hasRole("ADMIN")
                        // 그 외 /posts 조회·작성·수정 등 → 인증(유효 JWT) 필요
                        .anyRequest().authenticated()
                )
                // 기본은 미인증도 403을 주는 경우가 있어, API에 맞게 상태코드 구분
                // 401: 로그인/토큰 없음 · 403: 로그인은 됐는데 역할 부족
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            // UTF-8 안 주면 Postman 등에서 한글이 ??? 로 깨질 수 있음
                            response.setCharacterEncoding("UTF-8");
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.getWriter().write("{\"message\":\"인증이 필요합니다.\"}");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setCharacterEncoding("UTF-8");
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.getWriter().write("{\"message\":\"권한이 없습니다.\"}");
                        })
                )
                // JWT 필터를 UsernamePasswordAuthenticationFilter 앞에 등록
                // (폼 로그인 필터보다 먼저 Bearer 토큰을 처리)
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        // HttpSecurity 설정을 빌드한 SecurityFilterChain 객체를 빈으로 등록
        return http.build();
    }
}
