package com.practice.freeboard.adapter.out.mysql.repository;

import com.practice.freeboard.adapter.out.mysql.entity.MatchRequestEntity;
import com.practice.freeboard.domain.model.MatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 매칭 요청 JPA.
 * 수락/거절/취소/완료는 조건부 UPDATE → 영향 행 수(0 or 1)로 성공 여부 판단.
 */
public interface MatchRequestJpaRepository extends JpaRepository<MatchRequestEntity, Long> {

    /**
     * 선착순 수락.
     * status 가 REQUESTED 일 때만 ACCEPTED + vetId 세팅.
     * @return 갱신된 행 수 (1=성공, 0=이미 처리됨)
     */
    @Modifying(clearAutomatically = true)
    @Query("""
            update MatchRequestEntity m
               set m.status = :accepted,
                   m.acceptedVetId = :vetId
             where m.id = :id
               and m.status = :requested
            """)
    int acceptIfRequested(
            @Param("id") Long id,
            @Param("vetId") Long vetId,
            @Param("requested") MatchStatus requested,
            @Param("accepted") MatchStatus accepted
    );

    @Modifying(clearAutomatically = true)
    @Query("""
            update MatchRequestEntity m
               set m.status = :rejected
             where m.id = :id
               and m.status = :requested
            """)
    int rejectIfRequested(
            @Param("id") Long id,
            @Param("requested") MatchStatus requested,
            @Param("rejected") MatchStatus rejected
    );

    @Modifying(clearAutomatically = true)
    @Query("""
            update MatchRequestEntity m
               set m.status = :cancelled
             where m.id = :id
               and m.status = :requested
            """)
    int cancelIfRequested(
            @Param("id") Long id,
            @Param("requested") MatchStatus requested,
            @Param("cancelled") MatchStatus cancelled
    );

    @Modifying(clearAutomatically = true)
    @Query("""
            update MatchRequestEntity m
               set m.status = :completed
             where m.id = :id
               and m.status = :accepted
            """)
    int completeIfAccepted(
            @Param("id") Long id,
            @Param("accepted") MatchStatus accepted,
            @Param("completed") MatchStatus completed
    );
}
