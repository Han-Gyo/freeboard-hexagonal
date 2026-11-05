package com.practice.freeboard.adapter.out.mysql.repository;

import com.practice.freeboard.adapter.out.mysql.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// Comment 테이블 접근. 데모용 댓글 저장에 사용 (Comment API/UseCase 없음)
public interface CommentJpaRepository extends JpaRepository<CommentEntity, Long> {
}
