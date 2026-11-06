package com.practice.freeboard.adapter.out.mysql;

import com.practice.freeboard.adapter.out.mysql.entity.PostEntity;
import com.practice.freeboard.adapter.out.mysql.repository.PostJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// @Version이 있으면 옛 version으로 나중에 저장할 때 실패한다.
// 아래 주석 메서드는 @Version 없이 Lost Update가 나던 코드. 비교용이라 실행하지 않는다.
// @Transactional을 붙이지 않는다. 붙이면 A, B 조회가 한 트랜잭션이 되어 재현이 안 된다.
@SpringBootTest
public class LostUpdateDemo {

    @Autowired
    private PostJpaRepository postJpaRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    /*
    @Test
    void 나중에_저장한_내용이_먼저_저장한_내용을_덮어쓴다() {

        // 조회와 저장마다 커밋을 나누는 도구. 각자 읽은 값을 따로 들고 있게 하려고 쓴다.
        TransactionTemplate tx = new TransactionTemplate(transactionManager);

        // given - 원본 글을 먼저 커밋, 이후 A, B가 이 행을 각각 읽는다.
        Long id = tx.execute(status -> {
            PostEntity saved = postJpaRepository.save(
                    new PostEntity(null, "원본", "원본내용",
                            "작성자", LocalDateTime.now())
            );
            return saved.getId();
        });

        // 한 트랜잭션에서 findById를 두 번 하면 JPA가 같은 객체를 돌려준다.
        // 트랜잭션을 나눠야 A, B가 서로 다른 복사본을 갖는다.
        PostEntity readByA = tx.execute(status ->
                postJpaRepository.findById(id).orElseThrow()
        );
        PostEntity readByB = tx.execute(status ->
                postJpaRepository.findById(id).orElseThrow()
        );

        // when - A가 제목만 바꿔 먼저 저장.
        // 실제 UpdatePostAdapter도 id가 있는 엔티티를 save해서 행 전체를 다시 쓴다.
        tx.executeWithoutResult(status -> postJpaRepository.save(
                new PostEntity(id, "A수정", readByA.getContent(),
                        readByA.getAuthorName(), readByA.getCreatedAt())
        ));

        // B는 아직 제목 "원본"을 들고 있다. 내용만 바꿔 저장해도 제목까지 예전 값으로 덮인다.
        tx.executeWithoutResult(status -> postJpaRepository.save(
                new PostEntity(id, readByB.getTitle(), "B수정",
                        readByB.getAuthorName(), readByB.getCreatedAt())
        ));

        // then - 제목은 "원본", 내용은 "B수정". A의 제목 수정이 사라졌다.
        PostEntity result = tx.execute(status ->
                postJpaRepository.findById(id).orElseThrow()
        );
        assertThat(result.getTitle()).isEqualTo("원본");
        assertThat(result.getContent()).isEqualTo("B수정");

        // 이 테스트는 커밋이라 메서드 끝에 자동 롤백이 없다. 확인 후 연습 데이터를 지운다.
        tx.executeWithoutResult(status -> postJpaRepository.deleteById(id));
    }
    */

    @Test
    void 옛_version으로_나중에_저장하면_실패한다() {
        // 조회와 저장마다 커밋을 나눈다. A, B가 각자 읽은 version을 들고 있게 하려고 쓴다.
        TransactionTemplate tx = new TransactionTemplate(transactionManager);

        // given - 원본 글을 먼저 커밋. INSERT 때 version은 0이다.
        Long id = tx.execute(status -> {
            PostEntity saved = postJpaRepository.save(
                    new PostEntity(null, "원본", "원본내용", "작성자", LocalDateTime.now())
            );
            return saved.getId();
        });

        try {
            // A, B 둘 다 version 0을 읽는다.
            PostEntity readByA = tx.execute(status ->
                    postJpaRepository.findById(id).orElseThrow()
            );
            PostEntity readByB = tx.execute(status ->
                    postJpaRepository.findById(id).orElseThrow()
            );

            // A는 읽은 version 0으로 저장. 성공하면 DB version은 1이 된다.
            tx.executeWithoutResult(status -> postJpaRepository.save(
                    new PostEntity(id, "A수정", readByA.getContent(),
                            readByA.getAuthorName(), readByA.getCreatedAt(), readByA.getVersion())
            ));

            // B도 예전에 읽은 version 0으로 저장. DB는 이미 1이라 실패한다.
            assertThatThrownBy(() -> tx.executeWithoutResult(status -> postJpaRepository.save(
                    new PostEntity(id, readByB.getTitle(), "B수정",
                            readByB.getAuthorName(), readByB.getCreatedAt(), readByB.getVersion())
            ))).isInstanceOf(ObjectOptimisticLockingFailureException.class);

            // A의 제목이 그대로다. B의 내용 수정은 반영되지 않았다.
            PostEntity result = tx.execute(status ->
                    postJpaRepository.findById(id).orElseThrow()
            );
            assertThat(result.getTitle()).isEqualTo("A수정");
            assertThat(result.getContent()).isEqualTo("원본내용");
            assertThat(result.getVersion()).isEqualTo(1L);
        } finally {
            // 실패해도 연습 데이터는 지운다. 이 테스트는 자동 롤백이 없다.
            tx.executeWithoutResult(status -> postJpaRepository.deleteById(id));
        }
    }
}
