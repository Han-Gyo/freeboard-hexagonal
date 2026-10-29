package com.practice.freeboard.application.port.out;

import com.practice.freeboard.domain.model.Post;

public interface UpdatePostPort {

    // 여기서부터는 웹 단계가 아니라 앱 단계라서 도메인 타입으로 데이터를 다뤄야함 RequestVo하면 안 된다 ( id 포함임 도메인 타입이라 )
    void update (Post post);

}
