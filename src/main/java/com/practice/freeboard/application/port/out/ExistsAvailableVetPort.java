package com.practice.freeboard.application.port.out;

/**
 * 상담 가능(ON) 수의사가 한 명이라도 있는지.
 * 보호자 매칭 요청 입구에서 막으려고 씀.
 * → 0명이면 요청 INSERT 안 함 (DB에 헛요청 안 쌓임)
 */
public interface ExistsAvailableVetPort {

    /** true = ON 인 VET 1명 이상 */
    boolean existsAvailableVet();
}
