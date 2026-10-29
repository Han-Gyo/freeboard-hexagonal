package com.practice.freeboard.application.service;

import com.practice.freeboard.application.exception.PostNotFoundException;
import com.practice.freeboard.application.port.in.DeletePostUseCase;
import com.practice.freeboard.application.port.out.DeletePostPort;
import com.practice.freeboard.application.port.out.ReadOnePostPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class DeletePostService implements DeletePostUseCase {

    // 삭제 요청한 데이터가 DB에 있는지 조회하기 위해 Read_One때 사용하던 Port 재사용
    private ReadOnePostPort readOnePostPort;

    // 삭제 Port ( Adapter ) 객체 생성
    private DeletePostPort deletePostPort;

    @Override
    @Transactional // 조회, 삭제가 한 묶음으로 동작해야 함 하나라도 실패시 롤백 처리 해야함
    public void delete(Long id) {

        // 조회 먼저 하기 없을 경우 예외처리
        readOnePostPort.getById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        // 조회한 id 데이터 삭제하기
        deletePostPort.delete(id);

    }
}
