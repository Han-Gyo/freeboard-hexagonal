package com.practice.freeboard.adapter.out.mysql;

import com.practice.freeboard.adapter.out.mysql.repository.MemberJpaRepository;
import com.practice.freeboard.application.port.out.ExistsAvailableVetPort;
import com.practice.freeboard.domain.model.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * ON 인 수의사 존재 여부.
 * member 테이블에서 VET + matching_available=true 한 명만 있어도 true.
 */
@Component
@AllArgsConstructor
public class ExistsAvailableVetAdapter implements ExistsAvailableVetPort {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public boolean existsAvailableVet() {
        return memberJpaRepository.existsByRoleAndMatchingAvailable(Role.VET, true);
    }
}
