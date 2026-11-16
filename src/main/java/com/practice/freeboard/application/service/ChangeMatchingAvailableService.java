package com.practice.freeboard.application.service;

import com.practice.freeboard.application.exception.MemberNotFoundException;
import com.practice.freeboard.application.port.in.command.ChangeMatchingAvailableCommand;
import com.practice.freeboard.application.port.in.usecase.ChangeMatchingAvailableUseCase;
import com.practice.freeboard.application.port.out.LoginPort;
import com.practice.freeboard.application.port.out.UpdateMatchingAvailablePort;
import com.practice.freeboard.domain.exception.InvalidMemberException;
import com.practice.freeboard.domain.model.Member;
import com.practice.freeboard.domain.model.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 수의사 상담 가능 ON/OFF.
 * 흐름: 회원 조회 → 수의사인지 확인 → 도메인 ON/OFF → DB UPDATE
 */
@Service
@AllArgsConstructor
public class ChangeMatchingAvailableService implements ChangeMatchingAvailableUseCase {

    // 회원 조회는 로그인 쪽 Port 재사용 (id로 찾기)
    private final LoginPort loginPort;
    private final UpdateMatchingAvailablePort updateMatchingAvailablePort;

    @Override
    @Transactional
    public void changeMatchingAvailable(ChangeMatchingAvailableCommand command) {

        // 1) 누구인지 DB에서 가져오기
        Member member = loginPort.findById(command.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException(command.getMemberId()));

        // 2) 보호자(USER)는 라디오 의미 없음 → 수의사만
        if (member.getRole() != Role.VET) {
            throw new InvalidMemberException("수의사만 상담 가능 여부를 변경할 수 있습니다.");
        }

        // 3) 도메인 상태 바꾸기 (메모리)
        if (command.isAvailable()) {
            member.turnOnMatching();
        } else {
            member.turnOffMatching();
        }

        // 4) DB 컬럼 반영
        boolean ok = updateMatchingAvailablePort.updateMatchingAvailable(
                member.getId(),
                member.isMatchingAvailable()
        );
        if (!ok) {
            throw new MemberNotFoundException(command.getMemberId());
        }
    }
}
