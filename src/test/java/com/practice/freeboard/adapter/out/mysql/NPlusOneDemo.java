package com.practice.freeboard.adapter.out.mysql;

import com.practice.freeboard.adapter.out.mysql.entity.CommentEntity;
import com.practice.freeboard.adapter.out.mysql.entity.PostEntity;
import com.practice.freeboard.adapter.out.mysql.repository.CommentJpaRepository;
import com.practice.freeboard.adapter.out.mysql.repository.PostJpaRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

// N+1 해결 확인 데모 | findAllWithComments(Fetch Join)로 목록 조회 후
// 글마다 comments 접근해도 추가 SELECT가 없어야 함 (show-sql 로그로 확인)
@SpringBootTest
public class NPlusOneDemo {

    @Autowired
    private PostJpaRepository postJpaRepository;

    @Autowired
    private CommentJpaRepository commentJpaRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    void FetchJoin_후_댓글_접근하면_추가쿼리_없음(){
        // given - 글 3개, 각 글에 댓글 1개
        for(int i = 1; i <= 3; i++){
            PostEntity post = postJpaRepository.save(
                    new PostEntity(null, "제목" + i, "내용" + i, "작성자", LocalDateTime.now())
            );
            commentJpaRepository.save(new CommentEntity("댓글" + i, post));
        }

        // when - Fetch Join으로 게시글+댓글 한 번에 조회
        List<PostEntity> posts = postJpaRepository.findAllWithComments();

        // then - 글마다 댓글 접근. Fetch Join이면 여기서 +N 쿼리 없음 (로그 확인)
        System.out.println("===== 댓글 접근 시작 (Fetch Join이면 +N 없어야 함) =====");
        for (PostEntity post : posts) {
            post.getComments().size(); // 이미 로딩됐으면 추가 쿼리 없음
        }

    }

    @Test
    @Transactional
    void EntityGraph_후_댓글_접근하면_추가쿼리_없음(){
        // given - 글 3개, 각 글에 댓글 1개
        for(int i = 1; i <= 3; i++){
            PostEntity post = postJpaRepository.save(
                    new PostEntity(null, "제목" + i, "내용" + i, "작성자", LocalDateTime.now())
            );
            commentJpaRepository.save(new CommentEntity("댓글" + i, post));
        }

        // when - @EntityGraph로 게시글 + 댓글 한 번에 조회, 목적은 Fetch Join이랑 같음
        List<PostEntity> posts = postJpaRepository.findAllWithCommentsEntityGraph();

        // then - 글마다 댓글 접근, EntityGraph면 여기서 +N 쿼리 없음
        System.out.println("===== 댓글 접근 시작 (EntityGraph +N 없어야 함) =====");
        for(PostEntity post :posts) {
            post.getComments().size();
        }

        }

    // 페이징된 목록에 연관만 필요할 때 자주 쓴다
    @Test
    @Transactional // 테스트기 때문에 모든 작업이 정상 완료 되어도 롤백됨
    void batch_size면_댓글_조회가_IN으로_묶인다() {
        // given - 글 3개, 각 글에 댓글 1개씩
        for (int i = 1; i <= 3; i++) {
            PostEntity post = postJpaRepository.save(
                    new PostEntity(null, "제목" + i, "내용" + i,
                            "작성자", LocalDateTime.now())
            );
            commentJpaRepository.save(new CommentEntity("댓글" + i, post));
        }

        // 방금 저장한 Post는 메모리에 댓글 빈 리스트로 남아 있음
        // clear 후 findAll()로 DB에서 다시 읽어야 Lazy + batch가 동작함
        entityManager.flush();
        entityManager.clear();

        // when - join 없이 글만 조회
        List<PostEntity> posts = postJpaRepository.findAll();

        // then - 댓글 접근 시 글마다 SELECT가 아니라 IN 한 번이어야 함
        System.out.println("===== 댓글 접근 시작 (batch면 where post_id in 이어야 함) =====");
        for (PostEntity post : posts) {
            post.getComments().size();


        }
    }

}
