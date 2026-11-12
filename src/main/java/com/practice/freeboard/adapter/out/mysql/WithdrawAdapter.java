package com.practice.freeboard.adapter.out.mysql;

import com.practice.freeboard.adapter.out.mysql.repository.MemberJpaRepository;
import com.practice.freeboard.application.port.out.WithdrawPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 탈퇴 out port의 MySQL 구현.
 * 회원 행만 지운다. Refresh 폐기는 RefreshTokenAdapter 쪽.
 */
@Component
@AllArgsConstructor
public class WithdrawAdapter implements WithdrawPort {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public void deleteById(Long memberId) {
        // JpaRepository 기본 메서드. 해당 id 없으면 그냥 아무 일도 안 함(예외 X)
        memberJpaRepository.deleteById(memberId);
    }
}
