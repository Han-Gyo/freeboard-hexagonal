package com.practice.freeboard.adapter.out.mysql.repository;

import com.practice.freeboard.adapter.out.mysql.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long> {

    // N + 1 해결: Fetch Join 활용    Fetch Join = 조인해서 가져온다 의미
    // post와 comment를 한 번의 쿼리(JOIN)로 함께 로딩
    // distinct: 1:N join 시 Post 행이 댓글 수만큼 중복되는 것 완화
    @Query("select distinct p from PostEntity p left join fetch p.comments")
    List<PostEntity> findAllWithComments();

}
