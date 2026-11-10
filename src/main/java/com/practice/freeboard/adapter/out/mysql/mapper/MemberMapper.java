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
     * 회원가입 때 씀. id는 Entity 생성자에 없으니 INSERT 후 DB가 채운다.
     */
    public static MemberEntity toEntity(Member member) {
        return new MemberEntity(
                member.getEmail(),
                member.getPassword(),
                member.getRole(),
                member.getCreatedAt()
        );
    }

    /**
     * DB 행 -> 도메인 회원.
     * 로그인 성공 후 JWT에 넣을 id/role 을 도메인으로 넘길 때 사용.
     */
    public static Member toDomain(MemberEntity entity) {
        return new Member(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getRole(),
                entity.getCreatedAt()
        );
    }
}
