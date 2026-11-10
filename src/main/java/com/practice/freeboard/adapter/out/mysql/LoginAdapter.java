package com.practice.freeboard.adapter.out.mysql;

import com.practice.freeboard.adapter.out.mysql.mapper.MemberMapper;
import com.practice.freeboard.adapter.out.mysql.repository.MemberJpaRepository;
import com.practice.freeboard.application.port.out.LoginPort;
import com.practice.freeboard.domain.model.Member;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 로그인 out port의 MySQL 구현.
 * JPA로 이메일 조회 후 도메인 Member로만 바깥에 내보낸다.
 */
@Component
@AllArgsConstructor
public class LoginAdapter implements LoginPort {

    /** member 테이블 조회용 레포 */
    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Optional<Member> findByEmail(String email) {
        // Entity Optional → 있으면 toDomain, 없으면 empty 유지
        return memberJpaRepository.findByEmail(email)
                .map(MemberMapper::toDomain);
    }
}
