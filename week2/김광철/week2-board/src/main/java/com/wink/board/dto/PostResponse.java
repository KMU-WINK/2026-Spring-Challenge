package com.wink.board.dto;

import com.wink.board.domain.Post;

public record PostResponse(
        Long id,
        String title,
        String content,
        String writer,
        int likeCount
) {
    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getWriter(),
                post.getLikeCount()
        );
    }
}