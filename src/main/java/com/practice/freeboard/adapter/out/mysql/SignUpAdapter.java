package com.practice.freeboard.adapter.out.mysql;

import com.practice.freeboard.adapter.out.mysql.entity.MemberEntity;
import com.practice.freeboard.adapter.out.mysql.mapper.MemberMapper;
import com.practice.freeboard.adapter.out.mysql.repository.MemberJpaRepository;
import com.practice.freeboard.application.port.out.SignUpPort;
import com.practice.freeboard.domain.model.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 회원가입 out port의 MySQL 구현.
 * JPA/Entity는 이 어댑터 안에만 가둬 둔다.
 */
@Component
@AllArgsConstructor
public class SignUpAdapter implements SignUpPort {

    /** 회원 테이블 접근용 스프링 데이터 레포 */
    private final MemberJpaRepository memberJpaRepository;

    @Override
    public boolean existsByEmail(String email) {
        // 레포 메서드 이름 그대로 이메일 중복 여부
        return memberJpaRepository.existsByEmail(email);
    }

    @Override
    public Member save(Member member) {
        // 도메인 -> Entity 변환 후 INSERT
        MemberEntity saved = memberJpaRepository.save(MemberMapper.toEntity(member));

        // 저장된 행(id 포함)을 다시 도메인으로 돌려줌
        return MemberMapper.toDomain(saved);
    }
}
