package com.practice.freeboard.adapter.out.mysql;

import com.practice.freeboard.adapter.out.mysql.repository.MemberJpaRepository;
import com.practice.freeboard.application.port.out.UpdateMatchingAvailablePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 매칭 가능 ON/OFF DB 반영.
 * UPDATE member SET matching_available=? WHERE id=?
 */
@Component
@AllArgsConstructor
public class UpdateMatchingAvailableAdapter implements UpdateMatchingAvailablePort {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public boolean updateMatchingAvailable(Long memberId, boolean available) {
        int updated = memberJpaRepository.updateMatchingAvailable(memberId, available);
        // 1행 바뀌면 성공
        return updated == 1;
    }
}
