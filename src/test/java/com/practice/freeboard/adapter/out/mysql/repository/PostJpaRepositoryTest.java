package com.practice.freeboard.adapter.out.mysql.repository;

import com.practice.freeboard.adapter.out.mysql.entity.CommentEntity;
import com.practice.freeboard.adapter.out.mysql.entity.PostEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// DataJpaTest: Repository(JPA) 저장·조회가 되는지 검증 (Service Mock 테스트와 역할이 다름)
// JPA 관련 Bean만 로딩 (전체 @SpringBootTest / 웹 서버 X)
@DataJpaTest
// DataJpaTest 기본은 임베디드 DB로 바꾸려 함 → 로컬 MySQL(application.yml) 유지
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional // 테스트 끝나면 롤백 → DB에 연습 데이터 안 남김
// 역할: Service Mock 테스트와 다르게, Repository + DB(엔티티) 만 올려서 저장·조회가 되는지 보는 테스트.
class PostJpaRepositoryTest {

    @Autowired
    private PostJpaRepository postJpaRepository;

    @Autowired
    private CommentJpaRepository commentJpaRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void save_후_findAllWithComments_로_조회한다() {
        // given - Post + Comment 저장 (N+1 연습용 관계 데이터)
        // Comment만 post를 가리키고 저장함 → DB FK는 맞지만,
        // 메모리상 Post.comments 리스트에는 댓글이 안 들어감 (양방향 동기화 안 함)
        PostEntity post = postJpaRepository.save(
                new PostEntity(null, "제목", "내용", "작성자", LocalDateTime.now())
        );
        Long savedPostId = post.getId();
        commentJpaRepository.save(new CommentEntity("댓글", post));

        // flush: INSERT를 DB에 반영 / clear: 1차 캐시 비움
        // clear 없으면 findAllWithComments가 캐시에 남은 "댓글 빈 Post"를 그대로 돌려줄 수 있음
        entityManager.flush();
        entityManager.clear();

        // when - Fetch Join 메서드로 글+댓글 함께 조회 (DB에서 새로 로딩)
        List<PostEntity> posts = postJpaRepository.findAllWithComments();

        // then - 로컬 MySQL에는 예전 글(예: "테스트")이 남아 있을 수 있음
        // findAllWithComments()는 전체 목록이라 get(0)이 방금 저장한 글이 아닐 수 있음
        // → 저장할 때 받은 id로 이번 테스트 데이터만 골라서 검증
        PostEntity found = posts.stream()
                .filter(p -> p.getId().equals(savedPostId))
                .findFirst()
                .orElseThrow();

        assertThat(found.getTitle()).isEqualTo("제목");
        assertThat(found.getComments()).hasSize(1);
    }

}
