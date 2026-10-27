package com.practice.freeboard.adapter.out.mysql.repository;


import com.practice.freeboard.adapter.out.mysql.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
  PostEntity를 DB에 저장/조회하는 Spring Data JPA Repository.
  - JpaRepository를 상속하면 save(), findById() 등을 기본 제공함
  - 구현 클래스는 Spring이 자동 생성함 (직접 구현 X)
 **/
@Repository
public interface PostJpaRepository extends JpaRepository<PostEntity, Long> {

}
