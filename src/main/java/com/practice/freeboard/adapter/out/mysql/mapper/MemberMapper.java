package com.practice.freeboard.adapter.out.mysql.mapper;

import com.practice.freeboard.adapter.out.mysql.entity.MemberEntity;
import com.practice.freeboard.domain.model.Member;

/**
 * Entity <-> 도메인 Member 변환.
 * 어댑터 밖으로 Entity를 흘리지 않으려고 둔다.
 * (헥사: DB 모양이 바뀌어도 도메인/유스케이스는 Member만 보게)
 */
public final class MemberMapper {

    // 유틸 클래스라 new 로 안 쓰게 private 생성자
    private MemberMapper() {
    }

    /**
     * 도메인 회원 -> DB 저장용 Entity.
     * 가입·ON/OFF 저장 때 씀. matchingAvailable 도 같이 넣음.
     */
    public static MemberEntity toEntity(Member member) {
        return new MemberEntity(
                member.getEmail(),
                member.getPassword(),
                member.getRole(),
                member.isMatchingAvailable(),
                member.getCreatedAt()
        );
    }

    /**
     * DB 행 -> 도메인 회원.
     * 로그인·토글 전 조회 등. matchingAvailable 은 DB 값 그대로.
     */
    public static Member toDomain(MemberEntity entity) {
        return new Member(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getRole(),
                entity.isMatchingAvailable(),
                entity.getCreatedAt()
        );
    }
}
