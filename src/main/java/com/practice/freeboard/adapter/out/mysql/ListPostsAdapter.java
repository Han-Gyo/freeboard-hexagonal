package com.practice.freeboard.adapter.out.mysql;

import com.practice.freeboard.adapter.out.mysql.entity.PostEntity;
import com.practice.freeboard.adapter.out.mysql.mapper.PostMapper;
import com.practice.freeboard.adapter.out.mysql.repository.PostJpaRepository;
import com.practice.freeboard.application.port.in.command.ListPostsQuery;
import com.practice.freeboard.application.port.in.result.ListPostsResult;
import com.practice.freeboard.application.port.out.ListPostsPort;
import com.practice.freeboard.domain.model.Post;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;


// 게시글 목록 조회 출구 Adapter Query -> Spring Pageable 변환
// JPA Page 조회 후 Entity -> Domain -> ListPostsResult
@Component
@AllArgsConstructor
public class ListPostsAdapter implements ListPostsPort {

    // JPA 객체 생성
    private final PostJpaRepository postJpaRepository;
    // Mapper 객체 생성
    private final PostMapper postMapper;

    // 포트 구현 메인 흐름
    @Override
    public ListPostsResult listPosts(ListPostsQuery query) {

        // Application Query -> JPA가 아는 Pageable ( 변환은 adapter 책임 )
        Pageable pageable = toPageable(query);

        // DB 페이징 조회 ( JPA레포지토리가 기본 제공 ) DB에서 조건만큼 가져옴
        Page<PostEntity> page = postJpaRepository.findAll(pageable);

        // Entity -> Domain 변환
        List<Post> posts = page.getContent().stream()
                .map(postMapper::toDomain)
                .toList();

        // ListPostsResult 객체에 전체개수(TotalElements), 전체 페이지 수(TotalPages)담아 변환
        return new ListPostsResult(posts,
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize()
        );
    }

    // sort 문자열 ex) createdAt, desc
    // 파싱 실패 || 빈 값이 createdAt 내림차순 기본값 (최신글 먼저)
    // 스프링이 이해할 몇 페이지, 몇 개, 정렬 설정
    private Pageable toPageable(ListPostsQuery query){
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        if (query.getSort() != null && !query.getSort().isBlank()){
            String[] parts = query.getSort().split(",");
            String property = parts[0].trim();
            Sort.Direction direction = (parts.length > 1 && parts[1].trim().equalsIgnoreCase("asc"))
                    ? Sort.Direction.ASC
                    : Sort.Direction.DESC;
            sort = Sort.by(direction, property);
        }

        return PageRequest.of(query.getPage(), query.getSize(), sort);
    }

}
