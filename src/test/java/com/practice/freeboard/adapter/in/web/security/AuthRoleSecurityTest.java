package com.practice.freeboard.adapter.in.web.security;

import com.practice.freeboard.adapter.in.web.PostController;
import com.practice.freeboard.application.port.in.usecase.CreatePostUseCase;
import com.practice.freeboard.application.port.in.usecase.DeletePostUseCase;
import com.practice.freeboard.application.port.in.usecase.ListPostsUseCase;
import com.practice.freeboard.application.port.in.usecase.ReadPostUseCase;
import com.practice.freeboard.application.port.in.usecase.UpdatePostUseCase;
import com.practice.freeboard.domain.model.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 역할 인가가 실제로 막히는지 확인하는 테스트.
 *
 * @WebMvcTest: 웹(컨트롤러) + 시큐리티만 띄우고, DB는 안 켠다.
 * UseCase는 가짜 Bean으로 대체한다. (인가만 보면 되니까)
 * JWT는 JwtTokenProvider로 직접 만들어 Header에 붙인다.
 */
@WebMvcTest(controllers = PostController.class)
@AutoConfigureMockMvc
@Import({
        SecurityConfig.class,
        JwtAuthenticationFilter.class,
        JwtTokenProvider.class,
        AuthRoleSecurityTest.StubUseCases.class
})
@TestPropertySource(properties = {
        // 테스트용 시크릿 (발급·검증 같은 키)
        "jwt.secret=freeboard-local-jwt-secret-key-change-me-32bytes-min",
        "jwt.access-token-validity-ms=3600000"
})
class AuthRoleSecurityTest {

    /** HTTP 요청을 흉내 내는 테스트 도구 */
    @Autowired
    private MockMvc mockMvc;

    /** 테스트용 JWT 발급기 */
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * PostController 생성자에 들어갈 UseCase 가짜들.
     * DB/Service 없이 컨트롤러+시큐리티만 테스트하려고 만든다.
     */
    @TestConfiguration
    static class StubUseCases {

        @Bean
        CreatePostUseCase createPostUseCase() {
            return Mockito.mock(CreatePostUseCase.class);
        }

        @Bean
        ReadPostUseCase readPostUseCase() {
            return Mockito.mock(ReadPostUseCase.class);
        }

        @Bean
        UpdatePostUseCase updatePostUseCase() {
            return Mockito.mock(UpdatePostUseCase.class);
        }

        @Bean
        DeletePostUseCase deletePostUseCase() {
            // 기본: 아무 일도 안 함 → ADMIN이 인가 통과하면 컨트롤러가 200 반환
            return Mockito.mock(DeletePostUseCase.class);
        }

        @Bean
        ListPostsUseCase listPostsUseCase() {
            return Mockito.mock(ListPostsUseCase.class);
        }
    }

    @Test
    @DisplayName("삭제 요청에 토큰 없으면 401")
    void deleteWithoutToken_unauthorized() throws Exception {
        mockMvc.perform(delete("/posts/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("USER 토큰으로 삭제하면 403 (권한 부족)")
    void deleteWithUserRole_forbidden() throws Exception {
        // USER 역할만 담은 JWT 발급
        String token = jwtTokenProvider.createAccessToken(1L, Role.USER);

        mockMvc.perform(delete("/posts/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("ADMIN 토큰이면 인가 통과 후 200")
    void deleteWithAdminRole_ok() throws Exception {
        // ADMIN 역할 JWT → FilterChain hasRole("ADMIN") 통과 → 컨트롤러 실행
        String token = jwtTokenProvider.createAccessToken(1L, Role.ADMIN);

        mockMvc.perform(delete("/posts/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}
