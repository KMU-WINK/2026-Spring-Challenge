package com.wink.board.dto;

public record LikeResponse(
        Long postId,
        Long userId,
        long likeCount          // 이 글의 현재 좋아요 수
) { }
