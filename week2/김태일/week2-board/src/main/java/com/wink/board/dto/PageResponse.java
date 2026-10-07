package com.wink.board.dto;

import java.util.List;

/**
 * 페이징 응답
 * content: 현재 페이지의 글 목록
 * page: 현재 페이지 번호 (0부터 시작)
 * size: 페이지당 개수
 * totalElements: 전체 글 개수
 * totalPages: 전체 페이지 수
 * hasNext: 다음 페이지 존재 여부
 */
public record PageResponse<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext
) {
    public static <T> PageResponse<T> of(List<T> all, int page, int size) {
        int total = all.size();
        int totalPages = (int) Math.ceil((double) total / size);

        int from = (int) Math.min((long) page * size, total);   // 범위를 넘으면 빈 목록 (long: 큰 page 값 오버플로 방지)
        int to = Math.min(from + size, total);

        return new PageResponse<>(
                all.subList(from, to),
                page,
                size,
                total,
                totalPages,
                page + 1 < totalPages
        );
    }
}
